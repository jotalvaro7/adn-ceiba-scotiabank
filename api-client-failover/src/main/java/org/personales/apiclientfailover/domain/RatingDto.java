package org.personales.apiclientfailover.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {
    private Long id;
    private Long bookId;
    private Integer starts;
    private Integer port;
}

