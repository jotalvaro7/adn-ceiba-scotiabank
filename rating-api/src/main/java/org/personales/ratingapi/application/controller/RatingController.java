package org.personales.ratingapi.application.controller;

import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.api.RatingServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RatingController {

    @Autowired
    private RatingServicePort ratingServicePort;

    @GetMapping("/listar")
    public ResponseEntity<List<RatingDto>> getAllRatings() {
        return new ResponseEntity<>(ratingServicePort.getRatings(), HttpStatus.OK);
    }

    @GetMapping("/listar/{ratingId}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable Long ratingId) {
        return ratingServicePort.getRatingById(ratingId)
                .map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<RatingDto> addRating(@RequestBody RatingDto ratingDto) {
        return new ResponseEntity<>(ratingServicePort.addRating(ratingDto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{ratingId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long ratingId, @RequestBody RatingDto ratingDto){
        return ratingServicePort.updateRating(ratingId, ratingDto)
                .map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/eliminar/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long ratingId) {
        if(ratingServicePort.getRatingById(ratingId).isEmpty()) {
            return new ResponseEntity<>("Rating with Id: " + ratingId + " no encontrado" ,HttpStatus.NOT_FOUND);
        } else {
            ratingServicePort.deleteRatingById(ratingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
