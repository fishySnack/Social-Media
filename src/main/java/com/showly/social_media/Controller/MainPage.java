package com.showly.social_media.Controller;


import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainPage {
    
    @GetMapping("/")
    public String startPage() {
        return "profile";
    }
    

    @PostMapping("/saveProfile")
    public String saveProfile(
            @RequestParam("image") MultipartFile image,
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String bio,
            Model model,
            HttpSession session) throws IOException {
        
        String base64 = Base64.getEncoder().encodeToString(image.getBytes());
        String dataUrl = "data:" + image.getContentType() + ";base64," + base64;
        //model.addAttribute("imageData", dataUrl);
        
        //I will need to store these data in the database later on
        session.setAttribute("imageData", dataUrl);
        
        model.addAttribute("name", name);
        model.addAttribute("username", username);
        model.addAttribute("bio", bio);
        System.out.println("Received profile data:");
        System.out.println(name + " " + username + " " + bio); 

        return "homepage";
    }
}
