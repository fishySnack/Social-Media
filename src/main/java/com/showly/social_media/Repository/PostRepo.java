package com.showly.social_media.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showly.social_media.Data.Post;
import com.showly.social_media.Data.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
