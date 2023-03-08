package org.personales.apiclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto implements Serializable {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer port;
}
