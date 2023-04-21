package org.personales.bookapi.domain.data;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private String image;
    private Integer port;
}
