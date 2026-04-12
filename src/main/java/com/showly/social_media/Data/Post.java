package com.showly.social_media.Data;

import com.showly.social_media.Enum.MediaType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    @ManyToOne
    private User user;
}
