package com.workshop.MongoDb.Resources;

import com.workshop.MongoDb.Domain.Post;
import com.workshop.MongoDb.Resources.util.URL;
import com.workshop.MongoDb.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostResources {
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post post = postService.findById(id);

        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
        text = URL.decodeParam(text);
        List<Post> list = postService.findByTitle(text);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
                                                 @RequestParam(value = "minDate", defaultValue = "") String minDateStr,
                                                 @RequestParam(value = "maxDate", defaultValue = "") String maxDateStr) {
        text = URL.decodeParam(text);
        Instant minDate = minDateStr != null && !minDateStr.isEmpty() ? URL.convertDate(minDateStr) : Instant.EPOCH;
        Instant maxDate = maxDateStr != null && !maxDateStr.isEmpty() ? URL.convertDate(maxDateStr) : Instant.now();

        List<Post> list = postService.fullSearch(text, minDate, maxDate);
        return ResponseEntity.ok().body(list);
    }
}
