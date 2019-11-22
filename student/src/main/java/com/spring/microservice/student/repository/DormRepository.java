package com.spring.microservice.student.repository;

import com.spring.microservice.student.model.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormRepository extends JpaRepository<Dormitory, Long> {
    Dormitory findDormitoryById(long id);
    Dormitory findDormitoryByName(String name);
}
