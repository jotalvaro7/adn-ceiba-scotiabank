package org.personales.ratingapi.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.infrastructure.entity.Rating;
import org.personales.ratingapi.infrastructure.repository.RatingRepository;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatingJpaAdapterTest {

    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private Environment env;

    private RatingJpaAdapter ratingJpaAdapter;

    @BeforeEach
    void setUp() {
        ratingJpaAdapter = new RatingJpaAdapter(ratingRepository, modelMapper, env);
    }

    @Test
    void addBookTest() {
        RatingDto ratingDto = new RatingDto();
        Rating rating = new Rating();
        Rating ratingSaved = new Rating();

        when(modelMapper.map(ratingDto, Rating.class)).thenReturn(rating);
        when(ratingRepository.save(rating)).thenReturn(ratingSaved);
        when(modelMapper.map(ratingSaved, RatingDto.class)).thenReturn(ratingDto);

        RatingDto result = ratingJpaAdapter.addRating(ratingDto);

        assertEquals(result, ratingDto);
        verify(modelMapper).map(ratingDto, Rating.class);
        verify(ratingRepository).save(rating);
        verify(modelMapper).map(ratingSaved, RatingDto.class);
    }

    @Test
    void deleteRatingIdTest() {
        Long id = 1L;

        ratingJpaAdapter.deleteRatingById(id);

        verify(ratingRepository).deleteById(id);
    }

    @Test
    void updateBookTest() {
        Long id = 1L;
        RatingDto ratingDto = new RatingDto();
        Rating rating = new Rating();
        Rating ratingSaved = new Rating();


        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(rating)).thenReturn(ratingSaved);
        when(modelMapper.map(ratingSaved, RatingDto.class)).thenReturn(ratingDto);

        Optional<RatingDto> response = ratingJpaAdapter.updateRating(id, ratingDto);

        assertTrue(response.isPresent());
        assertEquals(ratingDto, response.get());
        verify(ratingRepository).findById(id);
        verify(ratingRepository).save(rating);

    }

    @Test
    void getRatingByIdTest(){
        Long id = 1L;
        int port = 8080;
        RatingDto ratingDto = new RatingDto();
        Rating rating = new Rating();

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(modelMapper.map(any(Rating.class), eq(RatingDto.class))).thenReturn(ratingDto);
        when(env.getProperty("local.server.port")).thenReturn(String.valueOf(port));

        Optional<RatingDto> result = ratingJpaAdapter.getRatingById(id);

        assertTrue(result.isPresent());
        assertEquals(ratingDto, result.get());
        verify(ratingRepository).findById(id);
    }

    @Test
    void getRatingsTest() {
        List<Rating> ratings = Arrays.asList(new Rating(), new Rating());
        List<RatingDto> booksDto = new ArrayList<>();
        booksDto.add(new RatingDto());
        booksDto.add(new RatingDto());
        int port = 8080;

        when(ratingRepository.findAll()).thenReturn(ratings);
        when(modelMapper.map(any(Rating.class), eq(RatingDto.class))).thenReturn(booksDto.get(0));
        when(env.getProperty("local.server.port")).thenReturn(String.valueOf(port));

        List<RatingDto> result = ratingJpaAdapter.getRatings();

        assertEquals(booksDto.get(0), result.get(0));
        verify(ratingRepository).findAll();
    }

}