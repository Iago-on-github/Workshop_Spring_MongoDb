package com.workshop.MongoDb.Dto;

import java.io.Serializable;

public record UserDto(String id,
                      String name,
                      String email) implements Serializable {}
