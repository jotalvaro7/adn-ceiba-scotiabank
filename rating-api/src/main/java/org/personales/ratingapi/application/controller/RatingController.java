package org.personales.ratingapi.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.api.RatingServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class RatingController {

    private final RatingServicePort ratingServicePort;

    @GetMapping("/listar")
    public ResponseEntity<List<RatingDto>> getAllRatings() {
        List<RatingDto> allRatings = ratingServicePort.getRatings();
        if(allRatings.isEmpty()){
           log.warn("No se han encontrado ratings");
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Lista de ratings: {}", allRatings);
            return new ResponseEntity<>(allRatings, HttpStatus.OK);
        }
    }

    @GetMapping("/listar/{ratingId}")
    public ResponseEntity<RatingDto> getRatingById(@PathVariable Long ratingId) {
        return ratingServicePort.getRatingById(ratingId)
                .map(rating -> {
                    log.info("rating con id: {} encontrado: {}", ratingId, rating);
                    return new ResponseEntity<>(rating, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("No se ha encontrado rating con id: {}", ratingId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping("/crear")
    public ResponseEntity<RatingDto> addRating(@RequestBody RatingDto ratingDto) {
        log.info("Creando nuevo rating: {}", ratingDto);
        return new ResponseEntity<>(ratingServicePort.addRating(ratingDto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{ratingId}")
    public ResponseEntity<RatingDto> updateRating(@PathVariable Long ratingId, @RequestBody RatingDto ratingDto){
        return ratingServicePort.updateRating(ratingId, ratingDto)
                .map(rating -> {
                    log.info("Rating actualizado: {}", rating);
                    return new ResponseEntity<>(rating, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("Rating no encontrado con ratingId: {}", ratingId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @DeleteMapping("/eliminar/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long ratingId) {
        if(ratingServicePort.getRatingById(ratingId).isEmpty()) {
            log.warn("Rating no encontrado con ratingId: {}", ratingId);
            return new ResponseEntity<>("Rating with Id: " + ratingId + " no encontrado" ,HttpStatus.NOT_FOUND);
        } else {
            log.info("Rating eliminado con ratingId: {}", ratingId);
            ratingServicePort.deleteRatingById(ratingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
