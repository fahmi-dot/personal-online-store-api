package com.fahmi.personalonlinestore.dto.response.other;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean hasPrev;
    private boolean hasNext;

    @Data
    @Builder
    public static class WithData<T> {
        private List<T> data;
        private PagedResponse pagination;
    }
}
