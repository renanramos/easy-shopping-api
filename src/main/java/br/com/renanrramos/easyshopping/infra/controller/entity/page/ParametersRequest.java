package br.com.renanrramos.easyshopping.infra.controller.entity.page;

import lombok.Data;

@Data
public class ParametersRequest {
    private int pageNumber;
    private int pageSize;
    private String sortBy;

    public ParametersRequest(final int pageNumber, final int pageSize, final String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
    }
}
