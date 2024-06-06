package com.example.lom.controllers;

import com.example.lom.models.Tag;
import com.example.lom.models.metingPayload.MetingPayload;
import com.example.lom.models.Meeting;
import com.example.lom.models.Subscription;
import com.example.lom.models.User;
import com.example.lom.models.metingPayload.UpdateMetingPayload;
import com.example.lom.repositories.TagRepository;
import com.example.lom.services.MeetingService;
import com.example.lom.services.SubscriptionService;
import com.example.lom.services.TagService;
import com.example.lom.services.UserService;
import jakarta.validation.Payload;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    private final UserService userService;

    private final SubscriptionService subscriptionService;

    private final TagRepository tagRepository;

    private final TagService tagService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MeetingController(MeetingService meetingService, UserService userService,
                             SubscriptionService subscriptionService, TagRepository tagRepository,
                             TagService tagService){
        this.meetingService = meetingService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String indexSearch(@RequestParam(value = "search", defaultValue = "") String name,
                              @RequestParam(value = "tag", defaultValue = "all") String tag, Model model) {
        var meetings = meetingService.getMeetings();
        if(!name.isEmpty()){
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }
        if(!tag.equals("all")){
            meetings = meetings.stream()
                    .filter(meeting -> meeting.getTags().contains(tagRepository.findByName(tag)))
                    .toList();
        }
        model.addAttribute("searchText", name);
        model.addAttribute("searchTag", tag);
        model.addAttribute("meetings", meetings);
        return "index";
    }

    @GetMapping("/{id}")
    public String meetingPage(Model model, @PathVariable String id, Authentication authentication) {
        User currentUser  = userService.getUserByUsername(authentication.getName());

        boolean isCreator = false;
        Meeting meeting = meetingService.getMeetingById(id).orElse(null);
        if(meeting != null) {
            isCreator= meeting.getCreator().equals(currentUser);
            Set<Tag> tag = meeting.getTags();
            Subscription subscription = this.subscriptionService.getSubscriptionByUserAndMeeting(currentUser, meeting).orElse(null);

            model.addAttribute("subscription", subscription);
            model.addAttribute("meeting", meeting);
            model.addAttribute("isCreator", isCreator);
            model.addAttribute("tags", tag);

            return "meeting";
        }
        return "redirect:/";
    }

    @GetMapping("/create")
    public String meetingCreatePage(Model model) {
        model.addAttribute("meeting", new Meeting());
        return "createMeeting";
    }

    @PostMapping("/create")
    public String createMeeting(@Valid MetingPayload payload, BindingResult bindingResult,
                                @RequestParam("image") MultipartFile imageFile,
                                Authentication authentication, Model model) throws IOException {
        User currentUser  = userService.getUserByUsername(authentication.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "createMeeting";
        }
        else {
            String imageUrl = null;

            if (imageFile != null && !imageFile.isEmpty()) {
                String imgDirPath = Paths.get(uploadPath).toString();
                File uploadDir = new File(imgDirPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path destinationPath = Paths.get(imgDirPath, uniqueFileName);
                Files.copy(imageFile.getInputStream(), destinationPath);
                imageUrl = "/img/" + uniqueFileName;
            }

            Meeting meeting = new Meeting(
                    payload.title(),
                    payload.details(),
                    payload.date(),
                    payload.place(),
                    payload.totalNumberSeats(),
                    payload.availableSeats(),
                    currentUser
            );
            meeting.setImageUrl(imageUrl);
            this.meetingService.addMeeting(meeting);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/delete/{id}", method={RequestMethod.DELETE, RequestMethod.POST})
    public String deleteMeeting(@PathVariable String id, Authentication authentication) {
        User currentUser = userService.getUserByUsername(authentication.getName());
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);
        if(meeting != null) {
            if(meeting.getCreator().equals(currentUser)){
                this.meetingService.deleteMeeting(id);
                return "redirect:/profile";
            }
        }
        return "redirect:/meetings/{id}";
    }

    @RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
    public String meetingEditPage(@PathVariable String id, Authentication authentication, Model model) {
        User currentUser  = userService.getUserByUsername(authentication.getName());
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);
        model.addAttribute("meeting", meeting);
        if (meeting == null || !meeting.getCreator().equals(currentUser))
            return "redirect:/";
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMeeting(@PathVariable String id,
                                Authentication authentication, @Valid UpdateMetingPayload payload,
                                BindingResult bindingResult, Model model) {
        User currentUser  = userService.getUserByUsername(authentication.getName());
        Meeting meeting = meetingService.getMeetingById(id).stream().findFirst().orElse(null);
        if(meeting == null) {
            return "redirect:/";
        }
        else if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "meetings/edit/{id}";
        }
        else {
                this.meetingService.updateMeeting(meeting);
                return "redirect:/meetings/{id}".formatted(meeting.getId());

        }
    }
}
