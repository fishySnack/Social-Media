package com.showly.social_media.Service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.showly.social_media.Data.Post;
import com.showly.social_media.Data.User;
import com.showly.social_media.Repository.PostRepo;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;

    public List<Post> getPostsByUser(User user) {
        return postRepo.findByUser(user);
    }

    public void save(Post post) {
        postRepo.save(post);
    }
}