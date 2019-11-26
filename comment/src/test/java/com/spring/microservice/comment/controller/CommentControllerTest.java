package com.spring.microservice.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.microservice.comment.model.Comment;
import com.spring.microservice.comment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    public void getComments() throws Exception {

        Date date1 = new Date();
        Date date2 = new Date();

        List<Comment> comments = Arrays.asList(
                new Comment(1,1, 1, "content1", date1),
                new Comment(2,1, 1, "content2", date2)
        );

        when(commentRepository.findAll()).thenReturn(comments);

        mockMvc.perform(get("/comments"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].student", is(1)))
                .andExpect(jsonPath("$[0].post", is(1)))
                .andExpect(jsonPath("$[0].content", is("content1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].student", is(1)))
                .andExpect(jsonPath("$[1].post", is(1)))
                .andExpect(jsonPath("$[1].content", is("content2")));


        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void getComment() throws Exception {

        Date date1 = new Date();

        Comment comment = new Comment(1,1, 1, "content1", date1);

        when(commentRepository.findCommentById(1)).thenReturn(comment);

        mockMvc.perform(get("/comments/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.student", is(1)))
                .andExpect(jsonPath("$.post", is(1)))
                .andExpect(jsonPath("$.content", is("content1")));

        verify(commentRepository, times(1)).findCommentById(1);
    }

    @Test
    void updateComment() throws Exception {

        Date date1 = new Date();

        Comment comment = new Comment(1,1, 1, "content1", date1);

        when(commentRepository.findCommentById(1)).thenReturn(comment);
        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(comment);

        mockMvc.perform(put("/comments/1")
                .content(om.writeValueAsString(comment))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.student", is(1)))
                .andExpect(jsonPath("$.post", is(1)))
                .andExpect(jsonPath("$.content", is("content1")));

        verify(commentRepository, times(1)).save(ArgumentMatchers.any(Comment.class));
    }

    @Test
    void deleteComment() throws Exception {

        Date date1 = new Date();

        Comment comment = new Comment(1,1, 1, "content1", date1);

        when(commentRepository.findCommentById(1)).thenReturn(comment);
        doNothing().when(commentRepository).delete(comment);

        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk());
    }
}