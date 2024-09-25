package com.workshop.MongoDb.Repositories;

import com.workshop.MongoDb.Domain.User;
import com.workshop.MongoDb.Dto.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
