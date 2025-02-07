package br.com.renanrramos.easyshopping.core.gateway;

import br.com.renanrramos.easyshopping.core.domain.ProductCategory;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import org.springframework.data.domain.Page;

public interface ProductCategoryGateway {

    ProductCategory save(final ProductCategory productCategory);

    Page<ProductCategory> findAll(final ParametersRequest parametersRequest);

    ProductCategory findById(final Long productCategoryId);

    ProductCategory update(final ProductCategory productCategory, final Long productCategoryId);

    void remove(final Long productCategoryId);

    Page<ProductCategory> findAllProductCategoriesByName(final ParametersRequest parametersRequest, final String name);
}
