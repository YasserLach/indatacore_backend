package com.indatacore.backend.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationResponse {
    private String field;
    private String message;

}
