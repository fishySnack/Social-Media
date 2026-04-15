package com.showly.social_media.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.showly.social_media.Data.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM users ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<User> findRandomUsers(@Param("limit") int limit);

    List<User> findByUsernameContainingIgnoreCase(String username);
}
