package com.ziola.shortenurl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponse {

    private String fullLengthLink;
    private Long visits;
}
