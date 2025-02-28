package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface ProductUseCase {

    ProductDTO save(final ProductForm productForm);

    PageResponse<ProductDTO> findAll(final ParametersRequest parametersRequest);

    ProductDTO findById(final Long productId);

    ProductDTO update(final ProductForm productForm, final Long productId);

    void remove(final Long productId);

    PageResponse<ProductDTO> findProductByStoreId(final ParametersRequest parametersRequest, final Long storeId);

    PageResponse<ProductDTO> getProductByName(final ParametersRequest parametersRequest, final String name);

    PageResponse<ProductDTO> findProductByCompanyId(final ParametersRequest parametersRequest, String companyId);

    PageResponse<ProductDTO> findProductBySubCategoryId(final ParametersRequest parametersRequest, Long subCategoryId);

    PageResponse<ProductDTO> getProductByCompanyIdAndName(final ParametersRequest parametersRequest, final String tokenId,
                                                          final String name);
}
