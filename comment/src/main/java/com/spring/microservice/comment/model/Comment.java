package com.spring.microservice.comment.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long student;
    private long post;

    private String content;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date_create;

    public Comment() {}

    public Comment(long student, long post, String content, Date date_create) {
        this.student = student;
        this.post = post;
        this.content = content;
        this.date_create = date_create;
    }

    public Comment(long id, long student, long post, String content, Date date_create) {
        this.id = id;
        this.student = student;
        this.post = post;
        this.content = content;
        this.date_create = date_create;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public long getPost() {
        return post;
    }

    public void setPost(long post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate_create() {
        return date_create;
    }

    public void setDate_create(Date date_create) {
        this.date_create = date_create;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", student=" + student +
                ", post=" + post +
                ", content='" + content + '\'' +
                ", date_create=" + date_create +
                '}';
    }
}
