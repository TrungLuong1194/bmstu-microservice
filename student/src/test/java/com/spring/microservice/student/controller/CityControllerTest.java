package com.spring.microservice.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.microservice.student.model.City;
import com.spring.microservice.student.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityRepository cityRepository;

    @org.junit.jupiter.api.Test
    void getCities() throws Exception {

        List<City> cities = Arrays.asList(
                new City(1,"Hanoi"),
                new City(2,"Moscow")
        );

        when(cityRepository.findAll()).thenReturn(cities);

        mockMvc.perform(get("/cities"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Hanoi")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Moscow")));

        verify(cityRepository, times(1)).findAll();
    }

    @org.junit.jupiter.api.Test
    void getCity() throws Exception {

        City city = new City(1,"Hanoi");

        when(cityRepository.findCityById(1)).thenReturn(city);

        mockMvc.perform(get("/cities/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hanoi")));

        verify(cityRepository, times(1)).findCityById(1);
    }

    @org.junit.jupiter.api.Test
    void createCity() throws Exception {
        City city = new City(1,"Hanoi");

        when(cityRepository.save(ArgumentMatchers.any(City.class))).thenReturn(city);

        mockMvc.perform(post("/cities")
                .content(om.writeValueAsString(city))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hanoi")));

        verify(cityRepository, times(1)).save(ArgumentMatchers.any(City.class));
    }

    @org.junit.jupiter.api.Test
    void updateCity() throws Exception {
        City city = new City(1,"Hanoi");

        when(cityRepository.findCityById(1)).thenReturn(city);
        when(cityRepository.save(ArgumentMatchers.any(City.class))).thenReturn(city);

        mockMvc.perform(put("/cities/1")
                .content(om.writeValueAsString(city))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hanoi")));

        verify(cityRepository, times(1)).save(ArgumentMatchers.any(City.class));
    }

    @Test
    void deleteCity() throws Exception {

        City city = new City(1,"Hanoi");

        when(cityRepository.findCityById(1)).thenReturn(city);
        doNothing().when(cityRepository).delete(city);

        mockMvc.perform(delete("/cities/1"))
                .andExpect(status().isOk());
    }
}