package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.ProductCategory;
import br.com.renanrramos.easyshopping.core.gateway.ProductCategoryGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductCategoryDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductCategoryForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class ProductCategoryUseCaseImpl implements ProductCategoryUseCase {

    private final ProductCategoryGateway productCategoryGateway;

    @Override
    public ProductCategoryDTO save(final ProductCategoryForm productCategoryForm) {
        final ProductCategory productCategory =
                ProductCategoryMapper.INSTANCE.mapProductCategoryFormToProductCategory(productCategoryForm);
        final ProductCategory productCategorySaved = productCategoryGateway.save(productCategory);
        return ProductCategoryMapper.INSTANCE.mapProductCategoryToProductCategoryDTO(productCategorySaved);
    }

    @Override
    public PageResponse<ProductCategoryDTO> findAll(final ParametersRequest parametersRequest) {
        final Page<ProductCategory> productCategoryPage = productCategoryGateway.findAll(parametersRequest);
        return new PageResponse<>(productCategoryPage.getTotalElements(), productCategoryPage.getTotalPages(),
                ProductCategoryMapper.INSTANCE.mapProductCategoryListToProductCategoryDTOList(productCategoryPage.getContent()));
    }

    @Override
    public ProductCategoryDTO findById(final Long productCategoryId) {
        return ProductCategoryMapper.INSTANCE
                .mapProductCategoryToProductCategoryDTO(productCategoryGateway.findById(productCategoryId));
    }

    @Override
    public ProductCategoryDTO update(final ProductCategoryForm productCategoryForm, final Long productCategoryId) {
        final ProductCategory productCategory =
                ProductCategoryMapper.INSTANCE.mapProductCategoryFormToProductCategory(productCategoryForm);
        return ProductCategoryMapper.INSTANCE
                .mapProductCategoryToProductCategoryDTO(productCategoryGateway.update(productCategory, productCategoryId));
    }

    @Override
    public void remove(final Long productCategoryId) {
        productCategoryGateway.remove(productCategoryId);
    }

    @Override
    public PageResponse<ProductCategoryDTO> findAllProductCategoriesByName(final ParametersRequest parametersRequest, final String name) {
        final Page<ProductCategory> productCategoriesByNamePage =
                productCategoryGateway.findAllProductCategoriesByName(parametersRequest, name);
        return new PageResponse<>(productCategoriesByNamePage.getTotalElements(),
                productCategoriesByNamePage.getTotalPages(),
                ProductCategoryMapper.INSTANCE
                        .mapProductCategoryListToProductCategoryDTOList(productCategoriesByNamePage.getContent()));
    }
}
