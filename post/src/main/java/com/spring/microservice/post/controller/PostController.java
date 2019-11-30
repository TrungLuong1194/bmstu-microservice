package com.spring.microservice.post.controller;

import com.spring.microservice.post.model.Post;
import com.spring.microservice.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @return List all posts
     */
    @GetMapping("/posts")
    public List<Post> getPosts() {

        return postRepository.findAll();
    }

    /**
     * @param id
     *
     * @return Get a post with id
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    /**
     * @param id
     *
     * @return Get a post with all comments
     */
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getPostWithComment(@PathVariable Long id) {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
        }

        String urlComment = "http://comment-service/comments/posts/{post}";

        String str = restTemplate.getForObject(urlComment, String.class, id);

        return new ResponseEntity<String>(str, HttpStatus.OK);
    }

    /**
     * @param student
     *
     * @return List all posts with student
     */
    @GetMapping("/posts/students/{student}")
    public ResponseEntity<?> getPostByStudent(@PathVariable Long student) {

        String url = "http://student-service/students/{id}";

        try {
            restTemplate.getForObject(url, String.class, student);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + student,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        List<Post> post = postRepository.findAllByStudent(student);

        return new ResponseEntity<List<Post>>(post, HttpStatus.OK);
    }

    /**
     * @param postDetails
     *
     * @implSpec Create a post with postDetails
     */
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody Post postDetails) {

        String url = "http://student-service/students/{id}";

        try {
            restTemplate.getForObject(url, String.class, postDetails.getStudent());
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + postDetails.getStudent(),
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

        postDetails.setDate_create(new Date());

        return new ResponseEntity<Post>(postRepository.save(postDetails), HttpStatus.OK);
    }

    /**
     * @param id, postDetails
     *
     * @implSpec Update a post with id
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @Valid @RequestBody Post postDetails) {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
        }

        postDetails.setDate_create(new Date());
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setDate_create(postDetails.getDate_create());

        Post updatedPost = postRepository.save(post);

        return new ResponseEntity<Post>(updatedPost, HttpStatus.OK);
    }

    /**
     * @param id
     *
     * @implSpec Delete a post with id
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        Post post = postRepository.findPostById(id);

        if (post == null) {
            return new ResponseEntity<String>("No post found for ID " + id, HttpStatus.NOT_FOUND);
        }

        String url = "http://comment-service/comments/posts/{post}";

        restTemplate.delete(url, id);

        postRepository.delete(post);

        return new ResponseEntity<String>("Delete successful!", HttpStatus.OK);
    }

    /**
     * @param student
     *
     * @implSpec Delete a post with student
     */
    @Transactional
    @DeleteMapping("/posts/students/{student}")
    public ResponseEntity<?> deletePostByStudent(@PathVariable Long student) {

        String url = "http://student-service/students/{id}";

        try {
            restTemplate.getForObject(url, String.class, student);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + student,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        String urlComment = "http://comment-service/comments/students/{student}";

        restTemplate.delete(urlComment, student);

        postRepository.deleteAllByStudent(student);

        return new ResponseEntity<String>("Delete successful!", HttpStatus.OK);
    }
}
