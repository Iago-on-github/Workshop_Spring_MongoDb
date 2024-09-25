package com.workshop.MongoDb.Services;

import com.workshop.MongoDb.Domain.User;
import com.workshop.MongoDb.Dto.UserDto;
import com.workshop.MongoDb.Repositories.UserRepository;
import com.workshop.MongoDb.Services.Exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException(id + " not found"));
    }

    public User insert(UserDto userDto) {
        User user = new User(userDto.id(), userDto.name(), userDto.email());
        return userRepository.insert(user);
    }

    public User update(String id, UserDto userDto) {
        if (!userRepository.existsById(id)) {
            throw new ObjectNotFoundException("User not found");
        }
        User user = findById(id);

        user.setName(userDto.name());
        user.setEmail(userDto.email());

        return userRepository.save(user);
    }

    public void delete(String id) {
        findById(id); //reproveitar o método para usar a verificação de Id
        userRepository.deleteById(id);
    }
}
