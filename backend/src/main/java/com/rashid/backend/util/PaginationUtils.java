package com.rashid.backend.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public final class PaginationUtils {

    private PaginationUtils() {
    }

    public static <T> Page<T> paginate(List<T> items, int page, int size) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.max(size, 1);
        int start = Math.min(normalizedPage * normalizedSize, items.size());
        int end = Math.min(start + normalizedSize, items.size());

        return new PageImpl<>(
                items.subList(start, end),
                PageRequest.of(normalizedPage, normalizedSize),
                items.size()
        );
    }
}
