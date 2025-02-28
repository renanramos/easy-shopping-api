package br.com.renanrramos.easyshopping.infra.delegate.specification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class ProductFilter {
    private Long storeId;
    private String name;
    private String companyId;
    private Long subCategoryId;
    private String tokenId;
}
