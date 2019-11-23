package com.spring.microservice.student.controller;

import com.spring.microservice.student.model.Student;
import com.spring.microservice.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    /**
     *
     * @return List all students
     */
    @GetMapping("/students")
    public List<Student> getStudents() {

        return studentRepository.findAll();
    }

    /**
     * @param id
     *
     * @return Get a student with id
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {

        Student student = studentRepository.findStudentById(id);

        if (student == null) {
            return new ResponseEntity<String>("No student found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    /**
     * @param studentDetails
     *
     * @implSpec Create a student with studentDetails
     */
    @PostMapping("/students")
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student studentDetails) {

        Student studentWithEmail = studentRepository.findStudentByEmail(studentDetails.getEmail());
        Student studentWithPhone = studentRepository.findStudentByPhone(studentDetails.getPhone());

        if (studentWithEmail != null) {
            return new ResponseEntity<String>("Haved student with email " + studentDetails.getEmail(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        if (studentWithPhone != null) {
            return new ResponseEntity<String>("Haved student with phone " + studentDetails.getPhone(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Student>(studentRepository.save(studentDetails), HttpStatus.OK);
    }

    /**
     * @param id, studentDetails
     *
     * @implSpec Update a student with id
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findStudentById(id);

        if (student == null) {
            return new ResponseEntity<String>("No student found for ID " + id, HttpStatus.NOT_FOUND);
        }

        student.setFirstname(studentDetails.getFirstname());
        student.setLastname(studentDetails.getLastname());
        student.setBirthday(studentDetails.getBirthday());
        student.setClassname(studentDetails.getClassname());
        student.setAddress(studentDetails.getAddress());
        student.setPhone(studentDetails.getPhone());
        student.setDescription(studentDetails.getDescription());
        student.setEmail(studentDetails.getEmail());
        student.setMajor(studentDetails.getMajor());
        student.setDorm(studentDetails.getDorm());
        student.setCity(studentDetails.getCity());

        Student updatedStudent = studentRepository.save(student);

        return new ResponseEntity<Student>(updatedStudent, HttpStatus.OK);
    }

    /**
     * @param id
     *
     * @implSpec Delete a student with id
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {

        Student student = studentRepository.findStudentById(id);

        if (student == null) {
            return new ResponseEntity<String>("No student found for ID " + id, HttpStatus.NOT_FOUND);
        }

        studentRepository.delete(student);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
