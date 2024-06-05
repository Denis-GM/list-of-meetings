package com.example.lom.controllers;

import com.example.lom.controllers.metingPayload.UpdateMetingPayload;
import com.example.lom.models.Meting;
import com.example.lom.services.MetingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("tests/{meetingId:\\d+}")

public class MetingController {

    private final MetingService meetingService;

    @ModelAttribute("meeting")
    public Meting meting(@PathVariable("meetingId") int metingId) {
        return this.meetingService.findMeeting(metingId).orElseThrow(() -> new NoSuchElementException("Митинг не найден"));
    }

    @GetMapping
    public String getMeeting(@PathVariable("meetingId") int meetingId, Model model) {
/*        model.addAttribute("meeting", this.meetingService.findMeeting(meetingId).orElseThrow());*/
        return "tests/meeting";
    }
    @GetMapping("edit")
    public String getMeetingEditPage(@PathVariable("meetingId") int meetingId,Model model) {
/*        model.addAttribute("meeting", this.meetingService.findMeeting(meetingId).orElseThrow());*/
        return "tests/edit";
    }

    @PostMapping("edit")
    public String updateMeeting(@ModelAttribute(value = "meeting", binding = false) Meting meting, @Valid UpdateMetingPayload payload,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "tests/edit";
        }
        else {
            if(payload.availableSeats()==null) {
                this.meetingService.updateMeting(meting.getId(),
                        payload.title(), payload.details(), payload.date(), payload.place(), payload.totalNumberSeats(), payload.totalNumberSeats());
                return "redirect:/tests/%d".formatted(meting.getId());
            }
            else {
                this.meetingService.updateMeting(meting.getId(),
                        payload.title(), payload.details(), payload.date(), payload.place(), payload.totalNumberSeats(), payload.availableSeats());
                return "redirect:/tests/%d".formatted(meting.getId());
            }
        }
    }

    @PostMapping("delete")
    public String deleteMeeting(@ModelAttribute("meeting") Meting meting) {
        this.meetingService.deleteMeeting(meting.getId());
        return "redirect:/tests/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                exception.getMessage());
        return "errors/404";
    }


}
