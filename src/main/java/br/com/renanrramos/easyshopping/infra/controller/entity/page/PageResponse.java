package br.com.renanrramos.easyshopping.infra.controller.entity.page;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {
    final Long totalElements;
    final Integer totalPages;
    final List<T> responseItems;

    public PageResponse(final Long totalElements, final Integer totalPages, final List<T> responseItems) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.responseItems = responseItems;
    }
}
