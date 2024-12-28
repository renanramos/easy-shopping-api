package br.com.renanrramos.easyshopping.infra.controller.entity.page;

import lombok.Getter;
import org.springframework.data.domain.Page;

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

    public static <T, E> PageResponse<T> buildPageResponse(final Page<E> page, final List<T> elements) {
        return new PageResponse<>(page.getTotalElements(), page.getTotalPages(), elements);
    }
}
