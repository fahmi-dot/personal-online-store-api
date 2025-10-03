package com.fahmi.personalonlinestore.util;

import com.fahmi.personalonlinestore.dto.response.other.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<CommonResponse<T>> response(HttpStatus status, String message, T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();

        commonResponse.setMessage(message);
        commonResponse.setStatusCode(status.value());
        commonResponse.setData(data);

        return ResponseEntity.status(status).body(commonResponse);
    }
}
