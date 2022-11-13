package org.personales.ratingapi.infrastructure.repository;

import org.personales.ratingapi.infrastructure.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
