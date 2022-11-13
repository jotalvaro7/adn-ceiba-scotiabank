package org.personales.ratingapi.domain.ports.api;

import org.personales.ratingapi.domain.data.RatingDto;

import java.util.List;
import java.util.Optional;

public interface RatingServicePort {
    RatingDto addRating(RatingDto ratingDto);
    void deleteRatingById(Long id);
    Optional<RatingDto> updateRating(Long id, RatingDto ratingDto);
    Optional<RatingDto> getRatingById(Long ratingId);
    List<RatingDto> getRatings();
}
