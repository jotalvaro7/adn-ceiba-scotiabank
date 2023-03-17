package org.personales.ratingapi.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.api.RatingServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingControllerTest {

    @Mock
    private RatingServicePort ratingServicePort;

    private RatingController ratingController;

    @BeforeEach
    void setUp() {
        ratingController = new RatingController(ratingServicePort);
    }

    @Test
    void getAllRatings_ShouldReturn200AndListOfRatings_WhenRatingsAreFound() {
        List<RatingDto> allRatings = Arrays.asList(
                new RatingDto(1L, 1L, 5, 8090),
                new RatingDto(2L, 2L, 5, 8090)
        );

        when(ratingServicePort.getRatings()).thenReturn(allRatings);

        ResponseEntity<List<RatingDto>> response = ratingController.getAllRatings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allRatings, response.getBody());
        verify(ratingServicePort).getRatings();

    }

    @Test
    void getAllRatings_ShouldReturn404_WhenRatingsNotFound() {
        when(ratingServicePort.getRatings()).thenReturn(Collections.emptyList());

        ResponseEntity<List<RatingDto>> response = ratingController.getAllRatings();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ratingServicePort).getRatings();

    }

    @Test
    void getRatingById_ShouldReturn200AndRating_WhenRatingByIdIsFound() {
        Long ratingId = 1L;
        RatingDto ratingDto = new RatingDto(1L, 1L, 5, 8090);

        when(ratingServicePort.getRatingById(ratingId)).thenReturn(Optional.of(ratingDto));

        ResponseEntity<RatingDto> response = ratingController.getRatingById(ratingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratingDto, response.getBody());

    }

    @Test
    void getRatingById_ShouldReturn400_WhenRatingByIdNotFound() {
        Long ratingId = 1L;
        Optional<RatingDto> optionalRatingDto = Optional.empty();

        when(ratingServicePort.getRatingById(ratingId)).thenReturn(optionalRatingDto);

        ResponseEntity<RatingDto> response = ratingController.getRatingById(ratingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ratingServicePort).getRatingById(ratingId);
    }

    @Test
    void addRating_ShouldReturnRating_whenRatingIsAdd() {
        RatingDto ratingDto = new RatingDto(1L, 1L, 5, 8090);

        when(ratingServicePort.addRating(ratingDto)).thenReturn(ratingDto);

        ResponseEntity<RatingDto> response = ratingController.addRating(ratingDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(ratingServicePort).addRating(ratingDto);
    }

    @Test
    void updateRating_ShouldReturn200AndRatingUpdate_WhenRatingIsFound() {
        Long ratingId = 1L;
        RatingDto ratingUpdateDto = new RatingDto(1L, 1L, 5, 8090);

        when(ratingServicePort.updateRating(ratingId, ratingUpdateDto)).thenReturn(Optional.of(ratingUpdateDto));

        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingId, ratingUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratingUpdateDto, response.getBody());
        verify(ratingServicePort).updateRating(ratingId, ratingUpdateDto);
    }

    @Test
    void updateRating_ShouldReturn404_WhenRatingNotFound() {
        Long ratingId = 1L;

        Optional<RatingDto> optionalRatingDto = Optional.empty();

        when(ratingServicePort.updateRating(ratingId, new RatingDto())).thenReturn(optionalRatingDto);

        ResponseEntity<RatingDto> response = ratingController.updateRating(ratingId, new RatingDto());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ratingServicePort).updateRating(ratingId, new RatingDto());

    }

    @Test
    void deleteRating_ShouldReturn204_WhenRatingIsFound() {
        Long ratingId = 1L;
        Optional<RatingDto> optionalRatingDto = Optional.of(new RatingDto());

        when(ratingServicePort.getRatingById(ratingId)).thenReturn(optionalRatingDto);

        ResponseEntity<?> response = ratingController.deleteRating(ratingId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ratingServicePort).getRatingById(ratingId);
        verify(ratingServicePort).deleteRatingById(ratingId);

    }

    @Test
    void deleteRating_ShouldReturn404_WhenRatingNotFound() {
        Long ratingId = 1L;
        Optional<RatingDto> optionalRatingDto = Optional.empty();

        when(ratingServicePort.getRatingById(ratingId)).thenReturn(optionalRatingDto);

        ResponseEntity<?> response = ratingController.deleteRating(ratingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(ratingServicePort).getRatingById(ratingId);

    }

}