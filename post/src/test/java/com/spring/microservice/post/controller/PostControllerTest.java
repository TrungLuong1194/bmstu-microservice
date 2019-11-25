package com.spring.microservice.post.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.microservice.post.model.Post;
import com.spring.microservice.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @Test
    void getPosts() throws Exception {

        Date date1 = new Date();
        Date date2 = new Date();

        List<Post> posts = Arrays.asList(
                new Post(1,1, "title1", "content1", date1),
                new Post(2,1, "title2", "content2", date2)
        );

        when(postRepository.findAll()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].student", is(1)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].content", is("content1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].student", is(1)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].content", is("content2")));


        verify(postRepository, times(1)).findAll();
    }

    @Test
    void getPost() throws Exception {
        Date date1 = new Date();

        Post post = new Post(1,1, "title1", "content1", date1);

        when(postRepository.findPostById(1)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.student", is(1)))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.content", is("content1")));

        verify(postRepository, times(1)).findPostById(1);
    }

    @Test
    void getPostByStudent() {
    }

    @Test
    void createPost() {
    }

    @Test
    void updatePost() throws Exception {

        Date date1 = new Date();

        Post post = new Post(1,1, "title1", "content1", date1);

        when(postRepository.findPostById(1)).thenReturn(post);
        when(postRepository.save(ArgumentMatchers.any(Post.class))).thenReturn(post);

        mockMvc.perform(put("/posts/1")
                .content(om.writeValueAsString(post))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.student", is(1)))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.content", is("content1")));

        verify(postRepository, times(1)).save(ArgumentMatchers.any(Post.class));
    }

    @Test
    void deletePost() throws Exception {

        Date date1 = new Date();

        Post post = new Post(1,1, "title1", "content1", date1);

        when(postRepository.findPostById(1)).thenReturn(post);
        doNothing().when(postRepository).delete(post);

        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isOk());
    }
}