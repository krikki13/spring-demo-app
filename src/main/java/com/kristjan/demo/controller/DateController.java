package com.kristjan.demo.controller;

import com.kristjan.demo.exception.UpdatingInPostMethodException;
import com.kristjan.demo.model.DateModel;
import com.kristjan.demo.repository.DateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DateController {
    @Autowired
    DateRepository dateRepository;

    /**
     * Returns all stored dates.
     */
    @GetMapping("/date")
    public List<DateModel> getAllDates() {
        return dateRepository.findAll();
    }

    /**
     * Adds new date.
     */
    @PostMapping("/date")
    public void addNewDate(@RequestBody DateModel date) {
        if(date.getId() != null && date.getId() != 0) {
            throw new UpdatingInPostMethodException();
        }
        date.setId(null);
        dateRepository.save(date);
    }
}