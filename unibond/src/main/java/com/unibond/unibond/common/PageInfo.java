package com.unibond.unibond.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageInfo {
    private Boolean lastPage;
    private int totalPages;
    private long totalElements;
    private int size;

    public <T> PageInfo(Page<T> page) {
        this.lastPage = page.isLast();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
    }
}
