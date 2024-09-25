package com.workshop.MongoDb.Config;

import com.workshop.MongoDb.Domain.Post;
import com.workshop.MongoDb.Domain.User;
import com.workshop.MongoDb.Dto.AuthorDto;
import com.workshop.MongoDb.Dto.CommentDto;
import com.workshop.MongoDb.Repositories.PostRepository;
import com.workshop.MongoDb.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration //fala pro Spring que essa é uma classe de configuração
public class Instantiation implements CommandLineRunner {
    //classe responsável por INSTANCIAR os objetos e salva-los no banco de dados

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss'Z'").withZone(ZoneId.of("GMT"));
        userRepository.deleteAll(); //limpa a coleção lá no MongoDb
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, ZonedDateTime.parse("21-09-2024T00:00:00Z", dt).toInstant(), "Let's go trip!", "I'm going to travel São Paulo. Hugs!", new AuthorDto(maria));
        Post post2 = new Post(null, ZonedDateTime.parse("18-08-2024T01:03:32Z", dt).toInstant(), "Good morning!", "I woke up happy today!", new AuthorDto(maria));

        CommentDto comment1 = new CommentDto("Have a nice trip, bro", ZonedDateTime.parse("24-08-2024T03:31:32Z", dt).toInstant(), new AuthorDto(alex));
        CommentDto comment2 = new CommentDto("Enjoy!", ZonedDateTime.parse("22-08-2024T12:02:36Z", dt).toInstant(), new AuthorDto(bob));
        CommentDto comment3 = new CommentDto("Have a nice day!", ZonedDateTime.parse("23-08-2024T13:11:30Z", dt).toInstant(), new AuthorDto(alex));

        post1.getComments().addAll(Arrays.asList(comment1, comment2)); //associar os comentários aos posts
        post2.getComments().add(comment3);

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2)); //adicionar os posts ao Objeto Maria
        userRepository.save(maria);
    }
}
