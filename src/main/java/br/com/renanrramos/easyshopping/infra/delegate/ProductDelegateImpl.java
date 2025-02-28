package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.ProductUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductDelegateImpl implements ProductDelegate {

    private final ProductUseCase productUseCase;

    @Override
    public ProductDTO save(final ProductForm productForm) {
        return null;
    }

    @Override
    public PageResponse<ProductDTO> findAll(final ParametersRequest parametersRequest) {
        return null;
    }

    @Override
    public ProductDTO findById(final Long productId) {
        return null;
    }

    @Override
    public ProductDTO update(final ProductForm productForm, final Long productId) {
        return null;
    }

    @Override
    public void remove(final Long productId) {

    }

    @Override
    public PageResponse<ProductDTO> findProductByStoreId(final ParametersRequest parametersRequest, final Long storeId) {
        return null;
    }

    @Override
    public PageResponse<ProductDTO> getProductByName(final ParametersRequest parametersRequest, final String name) {
        return null;
    }

    @Override
    public PageResponse<ProductDTO> findProductByCompanyId(final ParametersRequest parametersRequest,
                                                           final String companyId) {
        return null;
    }

    @Override
    public PageResponse<ProductDTO> findProductBySubCategoryId(final ParametersRequest parametersRequest,
                                                               final Long subCategoryId) {
        return null;
    }

    @Override
    public PageResponse<ProductDTO> getProductByCompanyIdAndName(final ParametersRequest parametersRequest,
                                                                 final String tokenId,
                                                                 final String name) {
        return null;
    }
}
