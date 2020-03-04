package com.spring.microservice.student.controller;

import com.spring.microservice.student.model.Major;
import com.spring.microservice.student.repository.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342", "http://localhost:63343" })
@RestController
public class MajorController {

    @Autowired
    private MajorRepository majorRepository;

    /**
     *
     * @return List all majors
     */
    @GetMapping("/majors")
    public List<Major> getMajors() {

        return majorRepository.findAll();
    }

    /**
     * @param id
     *
     * @return Get a major with id
     */
    @GetMapping("/majors/{id}")
    public ResponseEntity<?> getMajor(@PathVariable Long id) {

        Major major = majorRepository.findMajorById(id);

        if (major == null) {
            return new ResponseEntity<String>("No major found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Major>(major, HttpStatus.OK);
    }

    /**
     * @param majorDetails
     *
     * @implSpec Create a major with majorDetails
     */
    @PostMapping("/majors")
    public ResponseEntity<?> createMajor(@Valid @RequestBody Major majorDetails) {

        Major major = majorRepository.findMajorByName(majorDetails.getName());

        if (major != null) {
            return new ResponseEntity<String>("Haved major with name " + majorDetails.getName(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<Major>(majorRepository.save(majorDetails), HttpStatus.OK);
    }

    /**
     * @param id, majorDetails
     *
     * @implSpec Update a major with id
     */
    @PutMapping("/majors/{id}")
    public ResponseEntity<?> updateMajor(@PathVariable Long id, @Valid @RequestBody Major majorDetails) {

        Major major = majorRepository.findMajorById(id);

        if (major == null) {
            return new ResponseEntity<String>("No major found for ID " + id, HttpStatus.NOT_FOUND);
        }

        major.setName(majorDetails.getName());

        Major updatedMajor = majorRepository.save(major);

        return new ResponseEntity<Major>(updatedMajor, HttpStatus.OK);
    }

    /**
     * @param id
     *
     * @implSpec Delete a major with id
     */
    @DeleteMapping("/majors/{id}")
    public ResponseEntity<?> deleteMajor(@PathVariable Long id) {

        Major major = majorRepository.findMajorById(id);

        if (major == null) {
            return new ResponseEntity<String>("No major found for ID " + id, HttpStatus.NOT_FOUND);
        }

        majorRepository.delete(major);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
