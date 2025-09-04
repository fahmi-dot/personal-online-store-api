package com.fahmi.personalonlinestore.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {
    private String id;
    private String name;
}

