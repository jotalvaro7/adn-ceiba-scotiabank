package org.personales.ratingapi.domain.service;

import lombok.AllArgsConstructor;
import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.api.RatingServicePort;
import org.personales.ratingapi.domain.ports.spi.RatingPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingServicePort {
    private final RatingPersistencePort ratingPersistencePort;

    @Override
    public RatingDto addRating(RatingDto ratingDto) {
        return ratingPersistencePort.addRating(ratingDto);
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingPersistencePort.deleteRatingById(id);
    }

    @Override
    public Optional<RatingDto> updateRating(Long id, RatingDto ratingDto) {
        return ratingPersistencePort.updateRating(id, ratingDto);
    }

    @Override
    public Optional<RatingDto> getRatingById(Long ratingId) {
        return ratingPersistencePort.getRatingById(ratingId);
    }

    @Override
    public List<RatingDto> getRatings() {
        return ratingPersistencePort.getRatings();
    }
}
