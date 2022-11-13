package org.personales.ratingapi.domain.ports.spi;

import org.personales.ratingapi.domain.data.RatingDto;

import java.util.List;
import java.util.Optional;

public interface RatingPersistencePort {
    RatingDto addRating(RatingDto ratingDto);
    void deleteRatingById(Long id);
    Optional<RatingDto> updateRating(Long id, RatingDto ratingDto);
    Optional<RatingDto> getRatingById(Long ratingId);
    List<RatingDto> getRatings();
}
