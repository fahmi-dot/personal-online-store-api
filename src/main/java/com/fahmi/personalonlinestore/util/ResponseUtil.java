package com.fahmi.personalonlinestore.util;

import com.fahmi.personalonlinestore.dto.response.other.CommonResponse;
import com.fahmi.personalonlinestore.dto.response.other.PagedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<CommonResponse<T>> response(HttpStatus status, String message, T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();

        commonResponse.setMessage(message);
        commonResponse.setStatusCode(status.value());
        commonResponse.setData(data);
        commonResponse.setPagination(null);

        return ResponseEntity.status(status).body(commonResponse);
    }

    public static <T> ResponseEntity<CommonResponse<T>> responseWithPagination(HttpStatus status, String message, T data, PagedResponse<T> pagination) {
        CommonResponse<T> commonResponse = new CommonResponse<>();

        commonResponse.setMessage(message);
        commonResponse.setStatusCode(status.value());
        commonResponse.setData(data);
        commonResponse.setPagination(pagination);

        return ResponseEntity.status(status).body(commonResponse);
    }
}
