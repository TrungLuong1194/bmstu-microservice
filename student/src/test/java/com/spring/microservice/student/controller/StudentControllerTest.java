//package com.spring.microservice.student.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.spring.microservice.student.model.City;
//import com.spring.microservice.student.model.Dormitory;
//import com.spring.microservice.student.model.Major;
//import com.spring.microservice.student.model.Student;
//import com.spring.microservice.student.repository.StudentRepository;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class StudentControllerTest {
//
//    private static final ObjectMapper om = new ObjectMapper();
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StudentRepository studentRepository;
//
//    @Test
//    void getStudents() throws Exception {
//
//        City city = new City(1, "Hanoi");
//        Major major = new Major(1, "IU6");
//        Dormitory dorm = new Dormitory(1, "Dorm 9");
//        Date date1 = new Date();
//        Date date2 = new Date();
//
//        List<Student> students = Arrays.asList(
//                new Student(1, "Trung", "Luong", date1, "11M",
//                        "Address 1", "012345", "desc1", "email1", major, dorm, city),
//
//                new Student(2, "Trung2", "Luong2", date2, "11M",
//                        "Address 2", "012345678", "desc2", "email2", major, dorm, city)
//        );
//
//        when(studentRepository.findAll()).thenReturn(students);
//
//        mockMvc.perform(get("/students"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].firstname", is("Trung")))
//                .andExpect(jsonPath("$[0].lastname", is("Luong")))
//                .andExpect(jsonPath("$[0].classname", is("11M")))
//                .andExpect(jsonPath("$[0].address", is("Address 1")))
//                .andExpect(jsonPath("$[0].phone", is("012345")))
//                .andExpect(jsonPath("$[0].description", is("desc1")))
//                .andExpect(jsonPath("$[0].email", is("email1")));
//
//        verify(studentRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getStudent() throws Exception {
//
//        City city = new City(1, "Hanoi");
//        Major major = new Major(1, "IU6");
//        Dormitory dorm = new Dormitory(1, "Dorm 9");
//        Date date1 = new Date();
//
//        Student student = new Student(1, "Trung", "Luong", date1, "11M",
//                        "Address 1", "012345", "desc1", "email1", major, dorm, city);
//
//        when(studentRepository.findStudentById(1)).thenReturn(student);
//
//        mockMvc.perform(get("/students/1"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.firstname", is("Trung")))
//                .andExpect(jsonPath("$.lastname", is("Luong")))
//                .andExpect(jsonPath("$.classname", is("11M")))
//                .andExpect(jsonPath("$.address", is("Address 1")))
//                .andExpect(jsonPath("$.phone", is("012345")))
//                .andExpect(jsonPath("$.description", is("desc1")))
//                .andExpect(jsonPath("$.email", is("email1")));
//
//        verify(studentRepository, times(1)).findStudentById(1);
//    }
//
//    @Test
//    void createStudent() throws Exception {
//
//        City city = new City(1, "Hanoi");
//        Major major = new Major(1, "IU6");
//        Dormitory dorm = new Dormitory(1, "Dorm 9");
//        Date date1 = new Date();
//
//        Student student = new Student(1, "Trung", "Luong", date1, "11M",
//                "Address 1", "012345", "desc1", "email1", major, dorm, city);
//
//        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);
//
//        mockMvc.perform(post("/students")
//                .content(om.writeValueAsString(student))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.firstname", is("Trung")))
//                .andExpect(jsonPath("$.lastname", is("Luong")))
//                .andExpect(jsonPath("$.classname", is("11M")))
//                .andExpect(jsonPath("$.address", is("Address 1")))
//                .andExpect(jsonPath("$.phone", is("012345")))
//                .andExpect(jsonPath("$.description", is("desc1")))
//                .andExpect(jsonPath("$.email", is("email1")));
//
//        verify(studentRepository, times(1)).save(ArgumentMatchers.any(Student.class));
//    }
//
//    @Test
//    void updateStudent() throws Exception {
//
//        City city = new City(1, "Hanoi");
//        Major major = new Major(1, "IU6");
//        Dormitory dorm = new Dormitory(1, "Dorm 9");
//        Date date1 = new Date();
//
//        Student student = new Student(1, "Trung", "Luong", date1, "11M",
//                "Address 1", "012345", "desc1", "email1", major, dorm, city);
//
//        when(studentRepository.findStudentById(1)).thenReturn(student);
//        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);
//
//        mockMvc.perform(put("/students/1")
//                .content(om.writeValueAsString(student))
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.firstname", is("Trung")))
//                .andExpect(jsonPath("$.lastname", is("Luong")))
//                .andExpect(jsonPath("$.classname", is("11M")))
//                .andExpect(jsonPath("$.address", is("Address 1")))
//                .andExpect(jsonPath("$.phone", is("012345")))
//                .andExpect(jsonPath("$.description", is("desc1")))
//                .andExpect(jsonPath("$.email", is("email1")));
//
//        verify(studentRepository, times(1)).save(ArgumentMatchers.any(Student.class));
//    }
//
//    @Test
//    void deleteStudent() throws Exception {
//
//        City city = new City(1, "Hanoi");
//        Major major = new Major(1, "IU6");
//        Dormitory dorm = new Dormitory(1, "Dorm 9");
//        Date date1 = new Date();
//
//        Student student = new Student(1, "Trung", "Luong", date1, "11M",
//                "Address 1", "012345", "desc1", "email1", major, dorm, city);
//
//        when(studentRepository.findStudentById(1)).thenReturn(student);
//        doNothing().when(studentRepository).delete(student);
//
//        mockMvc.perform(delete("/students/1"))
//                .andExpect(status().isOk());
//    }
//}