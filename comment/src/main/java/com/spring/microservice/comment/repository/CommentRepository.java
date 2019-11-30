package com.spring.microservice.comment.repository;

import com.spring.microservice.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentById(long id);

    Comment findCommentByContent(String content);

    List<Comment> findAllByStudent(long student);

    List<Comment> findAllByPost(long post);

    void deleteAllByPost(long post);

    void deleteAllByStudent(long post);
}
