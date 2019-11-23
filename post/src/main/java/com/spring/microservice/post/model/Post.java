package com.spring.microservice.post.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long student_id;

    private String title;
    private String content;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date_create;

    public Post() {
    }

    public Post(long student_uid, String title, String content, Date date_create) {
        this.student_id = student_uid;
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

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
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
                ", student_uid='" + student_id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date_create=" + date_create +
                '}';
    }
}
