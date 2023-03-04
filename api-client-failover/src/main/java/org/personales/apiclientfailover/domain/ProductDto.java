package org.personales.apiclientfailover.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private BookDto bookDto;
    private RatingDto ratingDto;
    private Integer cantidad;

    public Double getTotalPrice(){
        return bookDto.getPrice() * cantidad.doubleValue();
    }
}
