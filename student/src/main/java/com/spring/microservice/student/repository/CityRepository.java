package com.spring.microservice.student.repository;

import com.spring.microservice.student.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City findCityById(long id);
    City findCityByName(String name);
}
