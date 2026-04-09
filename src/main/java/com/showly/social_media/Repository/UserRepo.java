package com.showly.social_media.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showly.social_media.Data.User;

@Repository
public interface UserRepo extends JpaRepository<UUID, User> {
    
}
