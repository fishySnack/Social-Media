package com.showly.social_media.Data;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class User {
    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private String username;
    private String[] bio;


}
