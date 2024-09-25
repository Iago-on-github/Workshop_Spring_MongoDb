package com.workshop.MongoDb.Services;

import com.workshop.MongoDb.Domain.Post;
import com.workshop.MongoDb.Repositories.PostRepository;
import com.workshop.MongoDb.Services.Exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        Optional<Post> post = postRepository.findById(id);

        return post.orElseThrow(() -> new ObjectNotFoundException(id + " Not found"));
    }

    public List<Post> findByTitle(String text) {
        return postRepository.searchTitle(text);
    }

    public List<Post> fullSearch(String text, Instant minDate, Instant maxDate) {
        maxDate = Instant.ofEpochMilli(maxDate.toEpochMilli() + 24 * 60 * 60 * 1000);
        return postRepository.fullSearch(text, minDate, maxDate);
    }
}
