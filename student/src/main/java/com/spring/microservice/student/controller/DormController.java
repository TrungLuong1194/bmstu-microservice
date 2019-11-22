package com.spring.microservice.student.controller;

import com.spring.microservice.student.model.Dormitory;
import com.spring.microservice.student.repository.DormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DormController {

    @Autowired
    private DormRepository dormRepository;

    @GetMapping("/dorms")
    public List<Dormitory> getDorms() {

        return dormRepository.findAll();
    }

    @GetMapping("/dorms/{id}")
    public ResponseEntity<?> getDorm(@PathVariable Long id) {

        Dormitory dorm = dormRepository.findDormitoryById(id);

        if (dorm == null) {
            return new ResponseEntity<String>("No dormitory found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Dormitory>(dorm, HttpStatus.OK);
    }

    @PostMapping("/dorms")
    public ResponseEntity<?> createDorm(@Valid @RequestBody Dormitory dormDetails) {

        Dormitory dorm = dormRepository.findDormitoryByName(dormDetails.getName());

        if (dorm != null) {
            return new ResponseEntity<String>("Haved dormitory with name " + dormDetails.getName(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Dormitory>(dormRepository.save(dormDetails), HttpStatus.OK);
    }

    @PutMapping("/dorms/{id}")
    public ResponseEntity<?> updateDorm(@PathVariable Long id, @Valid @RequestBody Dormitory dormDetails) {

        Dormitory dorm = dormRepository.findDormitoryById(id);

        if (dorm == null) {
            return new ResponseEntity<String>("No dormitory found for ID " + id, HttpStatus.NOT_FOUND);
        }

        dorm.setName(dormDetails.getName());

        Dormitory updatedDorm = dormRepository.save(dorm);

        return new ResponseEntity<Dormitory>(updatedDorm, HttpStatus.OK);
    }

    @DeleteMapping("/dorms/{id}")
    public ResponseEntity<?> deleteDorm(@PathVariable Long id) {

        Dormitory dorm = dormRepository.findDormitoryById(id);

        if (dorm == null) {
            return new ResponseEntity<String>("No dormitory found for ID " + id, HttpStatus.NOT_FOUND);
        }

        dormRepository.delete(dorm);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
