package com.showly.social_media.Controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.showly.social_media.Data.Post;
import com.showly.social_media.Data.User;
import com.showly.social_media.Enum.MediaType;
import com.showly.social_media.Repository.PostRepo;
import com.showly.social_media.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPage {

    private final UserRepo userRepo;
    private final PostRepo postRepo;

    @GetMapping("/")
    public String startPage(Model model) {

        User user = userRepo.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElse(new User());

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/homepage")
    public String homepage(Model model) {

        User user = userRepo.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElse(new User());

        List<Post> posts = postRepo.findAll();

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);

        return "homepage";
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

        User existing = userRepo.findByUsername(user.getUsername());

        if (existing != null) {
            user.setId(existing.getId());
            user.setImageData(existing.getImageData());
        }

        if (!image.isEmpty()) {
            String base64 = Base64.getEncoder().encodeToString(image.getBytes());
            String dataUrl = "data:" + image.getContentType() + ";base64," + base64;
            user.setImageData(dataUrl);
        }

        userRepo.save(user);

        return "redirect:/homepage";
    }

    @PostMapping("/addPost")
    public String addPost(
            @RequestParam("image") MultipartFile image) throws IOException {

        User user = userRepo.findAll()
                .stream()
                .reduce((first, second) -> second)
                .orElse(null);

        if (user == null || image.isEmpty()) {
            return "redirect:/homepage";
        }

        String base64 = Base64.getEncoder().encodeToString(image.getBytes());
        String dataUrl = "data:" + image.getContentType() + ";base64," + base64;

        Post post = new Post();
        post.setMediaUrl(dataUrl);
        post.setType(MediaType.IMAGE);
        post.setUser(user);

        postRepo.save(post);

        return "redirect:/homepage";
    }
}