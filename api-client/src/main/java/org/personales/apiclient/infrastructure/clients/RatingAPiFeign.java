package org.personales.apiclient.infrastructure.clients;

import org.personales.apiclient.domain.RatingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("msvc-rating-api")
public interface RatingAPiFeign {


    @GetMapping("/api/v1/listar")
    List<RatingDto> getAllRatings();


}
