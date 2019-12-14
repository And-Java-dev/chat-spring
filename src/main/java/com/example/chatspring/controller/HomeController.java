package com.example.chatspring.controller;

import com.example.chatspring.security.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    @GetMapping("/")
    public String home(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }

        return "index";
    }


}
