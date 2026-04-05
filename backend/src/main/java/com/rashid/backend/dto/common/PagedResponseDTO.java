package com.rashid.backend.dto.common;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResponseDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    public static <T> PagedResponseDTO<T> from(Page<T> pageData) {
        PagedResponseDTO<T> response = new PagedResponseDTO<>();
        response.setContent(pageData.getContent());
        response.setPage(pageData.getNumber());
        response.setSize(pageData.getSize());
        response.setTotalElements(pageData.getTotalElements());
        response.setTotalPages(pageData.getTotalPages());
        response.setFirst(pageData.isFirst());
        response.setLast(pageData.isLast());
        return response;
    }
}
