package com.spring.microservice.student.controller;

import com.spring.microservice.student.model.City;
import com.spring.microservice.student.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/cities")
    public List<City> getCities() {

        return cityRepository.findAll();
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<?> getCity(@PathVariable Long id) {

        City city = cityRepository.findCityById(id);

        if (city == null) {
            return new ResponseEntity<String>("No city found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<City>(city, HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<?> createCity(@Valid @RequestBody City cityDetails) {

        City city = cityRepository.findCityByName(cityDetails.getName());

        if (city != null) {
            return new ResponseEntity<String>("Haved city with name " + cityDetails.getName(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<City>(cityRepository.save(cityDetails), HttpStatus.OK);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<?> updateCity(@PathVariable Long id, @Valid @RequestBody City cityDetails) {

        City city = cityRepository.findCityById(id);

        if (city == null) {
            return new ResponseEntity<String>("No city found for ID " + id, HttpStatus.NOT_FOUND);
        }

        city.setName(cityDetails.getName());

        City updatedCity = cityRepository.save(city);

        return new ResponseEntity<City>(updatedCity, HttpStatus.OK);
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id) {

        City city = cityRepository.findCityById(id);

        if (city == null) {
            return new ResponseEntity<String>("No city found for ID " + id, HttpStatus.NOT_FOUND);
        }

        cityRepository.delete(city);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
