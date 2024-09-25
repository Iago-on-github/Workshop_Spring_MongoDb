package com.workshop.MongoDb.Resources;

import com.workshop.MongoDb.Domain.Post;
import com.workshop.MongoDb.Domain.User;
import com.workshop.MongoDb.Dto.UserDto;
import com.workshop.MongoDb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResources {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> userList = userService.findAll();
        List<UserDto> userDtoList = userList.stream().map(x -> new UserDto(x.getId(), x.getName(), x.getEmail())).toList();
        return ResponseEntity.ok().body(userDtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id) {
        User user = userService.findById(id);
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody UserDto userDto, UriComponentsBuilder componentsBuilder) {
        User user = userService.insert(userDto);
        URI uri = componentsBuilder.path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update (@RequestBody UserDto userDto, @PathVariable String id) {
        User user = userService.update(id, userDto);
        UserDto userDt = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok().body(userDt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> returnPosts(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user.getPosts());
    }
}
