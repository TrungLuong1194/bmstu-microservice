package com.spring.microservice.auth.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    private String role;
    private String firstname;
    private String lastname;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthday;

    private String classname;
    private String address;
    private String phone;
    private String description;
    private String email;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dormitory dorm;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Student() {
    }

    public Student(String username, String password, String role, String firstname, String lastname, Date birthday,
                   String classname, String address, String phone, String description, String email, Major major,
                   Dormitory dorm, City city) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.classname = classname;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.email = email;
        this.major = major;
        this.dorm = dorm;
        this.city = city;
    }

    public Student(long id, String username, String password, String role, String firstname, String lastname,
                   Date birthday, String classname, String address, String phone, String description, String email,
                   Major major, Dormitory dorm, City city) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.classname = classname;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.email = email;
        this.major = major;
        this.dorm = dorm;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Dormitory getDorm() {
        return dorm;
    }

    public void setDorm(Dormitory dorm) {
        this.dorm = dorm;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", classname='" + classname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", major=" + major +
                ", dorm=" + dorm +
                ", city=" + city +
                '}';
    }
}
