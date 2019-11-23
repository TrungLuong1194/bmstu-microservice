package com.spring.microservice.post.controller;

import com.spring.microservice.post.model.Post;
import com.spring.microservice.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/posts")
    public List<Post> getPosts() {

        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @PostMapping("students/{id}/posts")
    public ResponseEntity<?> createPost(@PathVariable Long id, @Valid @RequestBody Post postDetails) {

        String url = "http://student-service/students/{id}";

        try {
            restTemplate.getForObject(url, String.class, id);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + id,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        Post postWithTitle = postRepository.findPostByTitle(postDetails.getTitle());
        Post postWithContent = postRepository.findPostByContent(postDetails.getContent());

        if (postWithTitle != null) {
            return new ResponseEntity<String>("Haved post with title " + postDetails.getTitle(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        if (postWithContent != null) {
            return new ResponseEntity<String>("Haved post with content " + postDetails.getContent(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Post>(postRepository.save(postDetails), HttpStatus.OK);
    }

//    @PutMapping("/posts/{id}")
//    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid @RequestBody Post cityDetails) {
//
//        Post post = postRepository.findCityById(id);
//
//        if (post == null) {
//            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
//        }
//
//        post.setName(cityDetails.getName());
//
//        City updatedCity = postRepository.save(post);
//
//        return new ResponseEntity<Post>(updatedCity, HttpStatus.OK);
//    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
        }

        postRepository.delete(post);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
