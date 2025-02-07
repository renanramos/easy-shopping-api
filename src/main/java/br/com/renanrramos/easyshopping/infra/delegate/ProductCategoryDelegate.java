package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface ProductCategoryDelegate {
    ProductCategoryDTO save(final ProductCategoryForm productCategoryForm);

    ProductCategoryDTO findById(final Long productCategoryId);

    ProductCategoryDTO update(final ProductCategoryForm productCategoryForm, final Long productCategoryId);

    void remove(final Long productCategoryId);

    PageResponse<ProductCategoryDTO> findProductCategories(final ParametersRequest parametersRequest, final String name);
}
