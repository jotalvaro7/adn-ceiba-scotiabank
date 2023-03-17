package org.personales.ratingapi.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.spi.RatingPersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    private RatingPersistencePort ratingPersistencePort;

    private RatingServiceImpl ratingServiceImp;

    @BeforeEach
    void setUp() {
        ratingServiceImp = new RatingServiceImpl(ratingPersistencePort);
    }

    @Test
    void addRatingTest() {
        RatingDto ratingDto = new RatingDto();
        when(ratingPersistencePort.addRating(ratingDto)).thenReturn(ratingDto);

        RatingDto result = ratingServiceImp.addRating(ratingDto);

        assertEquals(ratingDto, result);
        verify(ratingPersistencePort).addRating(ratingDto);
    }

    @Test
    void deleteRatingByIdTest() {
        Long id = 1L;

        ratingServiceImp.deleteRatingById(id);

        verify(ratingPersistencePort).deleteRatingById(id);
    }

    @Test
    void updateRatingTest() {
        Long id = 1L;
        RatingDto ratingDto = new RatingDto();
        Optional<RatingDto> expected = Optional.of(ratingDto);
        when(ratingPersistencePort.updateRating(id, ratingDto)).thenReturn(expected);

        Optional<RatingDto> result = ratingServiceImp.updateRating(id, ratingDto);

        verify(ratingPersistencePort).updateRating(id, ratingDto);
        assertEquals(expected, result);

    }

    @Test
    void getBookByIdTest() {
        Long ratingId = 1L;
        RatingDto ratingDto = new RatingDto();
        Optional<RatingDto> expected = Optional.of(ratingDto);
        when(ratingPersistencePort.getRatingById(ratingId)).thenReturn(expected);

        Optional<RatingDto> result = ratingServiceImp.getRatingById(ratingId);

        assertEquals(expected, result);
        verify(ratingPersistencePort).getRatingById(ratingId);
    }

    @Test
    void getRatingsTest() {
        List<RatingDto> expected = new ArrayList<>();
        when(ratingPersistencePort.getRatings()).thenReturn(expected);

        List<RatingDto> result = ratingServiceImp.getRatings();

        assertEquals(expected, result);
        verify(ratingPersistencePort).getRatings();
    }
}