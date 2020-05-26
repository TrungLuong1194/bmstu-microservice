package com.spring.microservice.comment.controller;

import com.spring.microservice.comment.model.Comment;
import com.spring.microservice.comment.repository.CommentRepository;
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

@CrossOrigin(origins = { "http://localhost:63342", "http://localhost:63343" })
@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @return List all comments
     */
    @GetMapping("/comments")
    public List<Comment> getComments() {

        return commentRepository.findAll();
    }

    /**
     * @param id
     *
     * @return Get a comment with id
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {

        Comment comment = commentRepository.findCommentById(id);

        if (comment == null) {
            return new ResponseEntity<String>("No comment found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    /**
     * @param student
     *
     * @return List all comments with student
     */
    @GetMapping("/comments/students/{student}")
    public ResponseEntity<?> getCommentByStudent(@PathVariable Long student) {

        String url = "https://student-bmstu.herokuapp.com/students/{id}";

        try {
            restTemplate.getForObject(url, String.class, student);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + student,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        List<Comment> comments = commentRepository.findAllByStudent(student);

        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    /**
     * @param post
     *
     * @return List all comments with post
     */
    @GetMapping("/comments/posts/{post}")
    public ResponseEntity<?> getCommentByPost(@PathVariable Long post) {

        String url = "https://post-bmstu.herokuapp.com/posts/{id}";

        try {
            restTemplate.getForObject(url, String.class, post);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No post found for id " + post,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        List<Comment> comments = commentRepository.findAllByPost(post);

        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    /**
     * @param commentDetails
     *
     * @implSpec Create a comment with commentDetails
     */
    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@Valid @RequestBody Comment commentDetails) {

        String urlStudent = "https://student-bmstu.herokuapp.com/students/{id}";

        try {
            restTemplate.getForObject(urlStudent, String.class, commentDetails.getStudent());
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + commentDetails.getStudent(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        String urlPost = "https://post-bmstu.herokuapp.com/posts/{id}";

        try {
            restTemplate.getForObject(urlPost, String.class, commentDetails.getPost());
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No post found for id " + commentDetails.getPost(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        commentDetails.setDate_create(new Date());

        return new ResponseEntity<Comment>(commentRepository.save(commentDetails), HttpStatus.OK);
    }

    /**
     * @param id, commentDetails
     *
     * @implSpec Update a comment with id
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @Valid @RequestBody Comment commentDetails) {

        Comment comment = commentRepository.findCommentById(id);

        if (comment == null) {
            return new ResponseEntity<String>("No comment found for ID " + id, HttpStatus.NOT_FOUND);
        }

        commentDetails.setDate_create(new Date());
        comment.setStudent(commentDetails.getStudent());
        comment.setPost(commentDetails.getPost());
        comment.setContent(commentDetails.getContent());
        comment.setDate_create(commentDetails.getDate_create());

        Comment updatedComment = commentRepository.save(comment);

        return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);
    }

    /**
     * @param id
     *
     * @implSpec Delete a comment with id
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {

        Comment comment = commentRepository.findCommentById(id);

        if (comment == null) {
            return new ResponseEntity<String>("No comment found for ID " + id, HttpStatus.NOT_FOUND);
        }

        commentRepository.delete(comment);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }

    /**
     * @param post
     *
     * @implSpec Delete a comment with post
     */
    @Transactional
    @DeleteMapping("/comments/posts/{post}")
    public ResponseEntity<?> deleteCommentByPost(@PathVariable Long post) {

        String url = "https://post-bmstu.herokuapp.com/posts/{id}";

        try {
            restTemplate.getForObject(url, String.class, post);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No post found for id " + post,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        commentRepository.deleteAllByPost(post);

        return new ResponseEntity<Long>(post, HttpStatus.OK);
    }

    /**
     * @param student
     *
     * @implSpec Delete a comment with student
     */
    @Transactional
    @DeleteMapping("/comments/students/{student}")
    public ResponseEntity<?> deleteCommentByStudent(@PathVariable Long student) {

        String url = "https://student-bmstu.herokuapp.com/students/{id}";

        try {
            restTemplate.getForObject(url, String.class, student);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<String>("No student found for id " + student,
                    HttpStatus.NOT_ACCEPTABLE);
        }

        commentRepository.deleteAllByStudent(student);

        return new ResponseEntity<Long>(student, HttpStatus.OK);
    }
}
