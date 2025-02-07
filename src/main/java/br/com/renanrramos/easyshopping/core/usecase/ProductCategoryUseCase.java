package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface ProductCategoryUseCase {

    ProductCategoryDTO save(final ProductCategoryForm productCategoryForm);

    PageResponse<ProductCategoryDTO> findAll(final ParametersRequest parametersRequest);

    ProductCategoryDTO findById(final Long productCategoryId);

    ProductCategoryDTO update(final ProductCategoryForm productCategoryForm, final Long productCategoryId);

    void remove(final Long productCategoryId);

    PageResponse<ProductCategoryDTO> findAllProductCategoriesByName(final ParametersRequest parametersRequest, final String name);

}
