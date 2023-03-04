package org.personales.apiclientfailover.infrastructure.clients;

import org.personales.apiclientfailover.domain.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("msvc-rating-api")
public interface RatingAPiFeign {


    @GetMapping("/api/v1/listar")
    List<RatingDto> getAllRatings();

    @GetMapping("/api/v1/listar/{ratingId}")
    RatingDto getRatingById(@PathVariable Long ratingId);


}
