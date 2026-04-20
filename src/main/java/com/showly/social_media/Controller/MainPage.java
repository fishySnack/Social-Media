package com.showly.social_media.Controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.showly.social_media.Data.Post;
import com.showly.social_media.Data.User;
import com.showly.social_media.Enum.MediaType;
import com.showly.social_media.Service.PostService;
import com.showly.social_media.Service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainPage {

    private final UserService userService;
    private final PostService postService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String startPage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request) {

        userService.register(username, password);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String homepage(Authentication auth, Model model) {
        User user = userService.getByUsername(auth.getName());
        List<Post> posts = postService.getPostsByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "homepage";
    }

    @GetMapping("/profile")
    public String profilePage(Authentication auth, Model model) {
        User user = userService.getByUsername(auth.getName());
        model.addAttribute("name", user.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("bio", user.getBio());
        model.addAttribute("imageData", user.getImageData());
        return "profile";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam(required = false) String bio,
            @RequestParam("image") MultipartFile image,
            Authentication auth,
            Model model) throws IOException {

        if (name.isBlank() || username.isBlank()) {
            model.addAttribute("error", "Name and Username are required!");
            model.addAttribute("name", name);
            model.addAttribute("username", username);
            model.addAttribute("bio", bio);
            return "profile";
        }

        User user = userService.getByUsername(auth.getName());
        user.setName(name);
        user.setUsername(username);
        user.setBio(bio);

        if (!image.isEmpty()) {
            String base64 = Base64.getEncoder().encodeToString(image.getBytes());
            String dataUrl = "data:" + image.getContentType() + ";base64," + base64;
            user.setImageData(dataUrl);
        }

        userService.saveUser(user);
        return "redirect:/homepage";
    }

    @PostMapping("/addPost")
    public String addPost(
            @RequestParam("image") MultipartFile image,
            Authentication auth) throws IOException {

        if (image.isEmpty()) {
            return "redirect:/homepage";
        }

        User user = userService.getByUsername(auth.getName());

        String base64 = Base64.getEncoder().encodeToString(image.getBytes());
        String dataUrl = "data:" + image.getContentType() + ";base64," + base64;

        Post post = new Post();
        post.setMediaUrl(dataUrl);
        post.setType(MediaType.IMAGE);
        post.setUser(user);

        postService.save(post);
        return "redirect:/homepage";
    }

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> getPosts(Authentication auth) {
        User user = userService.getByUsername(auth.getName());
        return postService.getPostsByUser(user);
    }
}