package com.spring.microservice.student.repository;

import com.spring.microservice.student.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    Major findMajorById(long id);
    Major findMajorByName(String name);
}
