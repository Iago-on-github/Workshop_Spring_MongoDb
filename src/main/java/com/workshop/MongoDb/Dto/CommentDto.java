package com.workshop.MongoDb.Dto;

import java.time.Instant;

public record CommentDto(String text,
                         Instant date,
                         AuthorDto author) {
}
