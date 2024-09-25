package com.workshop.MongoDb.Dto;

import com.workshop.MongoDb.Domain.User;

public record AuthorDto(String id,
                        String name) {
    public AuthorDto(User user) {
        this(user.getId(), user.getName());
    }
}
