package com.showly.social_media.Controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.showly.social_media.Data.User;
import com.showly.social_media.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPage {

    private final UserRepo repo;

    @GetMapping("/")
    public String startPage(Model model) {

        User user = repo.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElse(new User());

        model.addAttribute("user", user);

        return "profile";
    }
    @PostMapping("/saveProfile")
    public String saveProfile(
            @ModelAttribute User user,
            @RequestParam("image") MultipartFile image,
            Model model) throws IOException {

        boolean invalid =
                user.getName() == null || user.getName().isBlank() ||
                user.getUsername() == null || user.getUsername().isBlank();

        if (invalid) {

            model.addAttribute("error", "Name and Username are required!");
            model.addAttribute("user", user);

            return "profile";
        }

        User existing = repo.findByUsername(user.getUsername());

        if (existing != null) {
            user.setId(existing.getId());
            user.setImageData(existing.getImageData());
        }

        if (!image.isEmpty()) {
            String base64 = Base64.getEncoder().encodeToString(image.getBytes());
            String dataUrl = "data:" + image.getContentType() + ";base64," + base64;
            user.setImageData(dataUrl);
        }

        repo.save(user);

        return "homepage";
    }
    
}