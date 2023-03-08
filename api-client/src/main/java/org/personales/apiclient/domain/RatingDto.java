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
public class RatingDto implements Serializable {
    private Long id;
    private Long bookId;
    private Integer starts;
    private Integer port;
}

