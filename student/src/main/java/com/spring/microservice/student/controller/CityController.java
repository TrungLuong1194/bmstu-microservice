package com.spring.microservice.student.controller;

import com.spring.microservice.student.model.City;
import com.spring.microservice.student.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:63342", "http://localhost:63343" })
@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    /**
     *
     * @return List all cities
     */
    @GetMapping("/cities")
    public List<City> getCities() {

        return cityRepository.findAll();
    }

    /**
     * @param id
     *
     * @return Get a city with id
     */
    @GetMapping("/cities/{id}")
    public ResponseEntity<?> getCity(@PathVariable Long id) {

        City city = cityRepository.findCityById(id);

        if (city == null) {
            return new ResponseEntity<String>("No city found for ID " + id, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<City>(city, HttpStatus.OK);
    }

    /**
     * @param cityDetails
     *
     * @implSpec Create a city with cityDetails
     */
    @PostMapping("/cities")
    public ResponseEntity<?> createCity(@Valid @RequestBody City cityDetails) {

        City city = cityRepository.findCityByName(cityDetails.getName());

        if (city != null) {
            return new ResponseEntity<String>("Haved city with name " + cityDetails.getName(),
                    HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<City>(cityRepository.save(cityDetails), HttpStatus.OK);
    }

    /**
     * @param id, cityDetails
     *
     * @implSpec Update a city with id
     */
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

    /**
     * @param id
     *
     * @implSpec Delete a city with id
     */
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
