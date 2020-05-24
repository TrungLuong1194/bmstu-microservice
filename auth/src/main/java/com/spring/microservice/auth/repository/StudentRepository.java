package com.spring.microservice.auth.repository;

import com.spring.microservice.auth.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentById(long id);

    Student findStudentByEmail(String email);

    Student findStudentByPhone(String phone);

    Student findStudentByUsername(String username);
}
