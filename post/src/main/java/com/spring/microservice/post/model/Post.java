package com.spring.microservice.post.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long student;

    private String title;
    private String content;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date_create;

    public Post() {
    }

    public Post(long student, String title, String content, Date date_create) {
        this.student = student;
        this.title = title;
        this.content = content;
        this.date_create = date_create;
    }

    public Post(long id, long student, String title, String content, Date date_create) {
        this.id = id;
        this.student = student;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "Post{" +
                "id=" + id +
                ", student='" + student + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date_create=" + date_create +
                '}';
    }
}
