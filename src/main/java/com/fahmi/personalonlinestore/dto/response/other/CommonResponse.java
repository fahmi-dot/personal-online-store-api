package com.fahmi.personalonlinestore.dto.response.other;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private String message;
    private int statusCode;
    private T data;
}
