package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.usecase.ProductCategoryUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class ProductCategoryDelegateImpl implements ProductCategoryDelegate {

    private final ProductCategoryUseCase productCategoryUseCase;

    @Override
    public ProductCategoryDTO save(final ProductCategoryForm productCategoryForm) {
        return productCategoryUseCase.save(productCategoryForm);
    }

    @Override
    public ProductCategoryDTO findById(final Long productCategoryId) {
        return productCategoryUseCase.findById(productCategoryId);
    }

    @Override
    public ProductCategoryDTO update(final ProductCategoryForm productCategoryForm, final Long productCategoryId) {
        return productCategoryUseCase.update(productCategoryForm, productCategoryId);
    }

    @Override
    public void remove(final Long productCategoryId) {
        productCategoryUseCase.remove(productCategoryId);
    }

    @Override
    public PageResponse<ProductCategoryDTO> findProductCategories(final ParametersRequest parametersRequest,
                                                                  final String name) {
        return (StringUtils.isEmpty(name)) ?
                productCategoryUseCase.findAll(parametersRequest) :
                productCategoryUseCase.findAllProductCategoriesByName(parametersRequest, name);
    }
}
