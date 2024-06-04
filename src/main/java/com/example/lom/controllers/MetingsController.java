package com.example.lom.controllers;

import com.example.lom.controllers.metingPayload.MetingPayload;
import com.example.lom.models.Meting;
import com.example.lom.services.MetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("tests")
@RequiredArgsConstructor
public class MetingsController {
    private final MetingService meetingService;

    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("meetings", this.meetingService.findAllMetings());
        return "tests/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "tests/new_meeting";
    }

    @PostMapping("create")
    public String createProduct(@Valid MetingPayload payload, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "tests/new_meeting";
        }
        else {
            Meting meting = this.meetingService.createMeting(payload.title(), payload.details(), payload.date());
            return "redirect:/tests/list";
        }
    }
}
