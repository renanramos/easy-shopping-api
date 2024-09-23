package br.com.renanrramos.easyshopping.infra.controller.entity.page;

import lombok.Data;

@Data
public class ParametersRequest {

    public static int DEFAULT_PAGE_NUMBER = 1;
    public static int DEFAULT_PAGE_SIZE = 10;
    public static String DEFAULT_SORT_BY = "asc";

    private int pageNumber;
    private int pageSize;
    private String sortBy;

    public ParametersRequest(final int pageNumber, final int pageSize, final String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
    }

    public ParametersRequest() {
        this.pageNumber = DEFAULT_PAGE_NUMBER;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.sortBy = DEFAULT_SORT_BY;
    }
}