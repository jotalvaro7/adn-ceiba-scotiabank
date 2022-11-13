package org.personales.ratingapi.application.controller;

import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.api.RatingServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingServicePort ratingServicePort;

    @GetMapping()
    private ResponseEntity<List<RatingDto>> getAllRatings() {
        return new ResponseEntity<>(ratingServicePort.getRatings(), HttpStatus.OK);
    }

}
