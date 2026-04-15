package com.showly.social_media.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showly.social_media.Data.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    
}
