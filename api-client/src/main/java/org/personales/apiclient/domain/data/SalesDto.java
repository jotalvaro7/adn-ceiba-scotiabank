package org.personales.apiclient.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDto {
    private BookDto bookDto;
    private RatingDto ratingDto;
    private Integer cantidad;

    public Double getTotalPrice(){
        return bookDto.getPrice() * cantidad.doubleValue();
    }
}
