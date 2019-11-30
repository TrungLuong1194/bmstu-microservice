package com.spring.microservice.post.repository;

import com.spring.microservice.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	
    Post findPostById(long id);

    Post findPostByTitle(String title);

    Post findPostByContent(String content);

    List<Post> findAllByStudent(long student);

    void deleteAllByStudent(long student);
}
