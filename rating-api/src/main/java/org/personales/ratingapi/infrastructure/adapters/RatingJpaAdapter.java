package org.personales.ratingapi.infrastructure.adapters;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.personales.ratingapi.domain.data.RatingDto;
import org.personales.ratingapi.domain.ports.spi.RatingPersistencePort;
import org.personales.ratingapi.infrastructure.entity.Rating;
import org.personales.ratingapi.infrastructure.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RatingJpaAdapter implements RatingPersistencePort {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Environment env;


    @Override
    @Transactional
    public RatingDto addRating(RatingDto ratingDto) {
        Rating rating = modelMapper.map(ratingDto, Rating.class);
        Rating ratingSaved = ratingRepository.save(rating);
        return modelMapper.map(ratingSaved, RatingDto.class);
    }

    @Override
    @Transactional
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<RatingDto> updateRating(Long id, RatingDto ratingDto) {
        return ratingRepository.findById(id).map(rating -> {
            rating.setBookId(ratingDto.getBookId());
            rating.setStarts(ratingDto.getStarts());
            return modelMapper.map(ratingRepository.save(rating), RatingDto.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RatingDto> getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId).map(rating -> {
            RatingDto ratingDto = modelMapper.map(rating, RatingDto.class);
            ratingDto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            return ratingDto;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingDto> getRatings() {
        return ratingRepository.findAll().stream()
                .map(rating -> {
                    RatingDto ratingDto = modelMapper.map(rating, RatingDto.class);
                    ratingDto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
                    return ratingDto;
                })
                .collect(Collectors.toList());
    }
}
