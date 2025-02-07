package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.ProductImage;
import br.com.renanrramos.easyshopping.core.gateway.ProductImageGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductImageDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductImageForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class ProductImageUseCaseImpl implements ProductImageUseCase {

    private final ProductImageGateway productImageGateway;

    @Override
    public ProductImageDTO save(final ProductImageForm productImageForm) {
        final ProductImage productImage =
                ProductImageMapper.INSTANCE.mapProductImageFormToProductImage(productImageForm);
        final ProductImage newProductImage = productImageGateway.save(productImage);
        return ProductImageMapper.INSTANCE.mapProductImageToProductImageDTO(newProductImage);
    }

    @Override
    public PageResponse<ProductImageDTO> findAll(final ParametersRequest parametersRequest) {
        final Page<ProductImage> productImagePage = productImageGateway.findAll(parametersRequest);
        return new PageResponse<>(productImagePage.getTotalElements(), productImagePage.getTotalPages(),
                ProductImageMapper.INSTANCE.mapProductImageListToProductImageDTOList(productImagePage.getContent()));
    }

    @Override
    public ProductImageDTO findById(final Long productImageId) {
        final ProductImage productImage = productImageGateway.findById(productImageId);
        return ProductImageMapper.INSTANCE.mapProductImageToProductImageDTO(productImage);
    }

    @Override
    public ProductImageDTO update(final ProductImageForm productImageForm, final Long productImageId) {
        final ProductImage productImage = ProductImageMapper.INSTANCE.mapProductImageFormToProductImage(productImageForm);
        final ProductImage productImageUpdated = productImageGateway.update(productImage, productImageId);
        return ProductImageMapper.INSTANCE.mapProductImageToProductImageDTO(productImageUpdated);
    }

    @Override
    public void remove(final Long productImageId) {
        productImageGateway.remove(productImageId);
    }

    @Override
    public ProductImageDTO getImageByProductId(final Long productId) {
        final ProductImage productImage = productImageGateway.getImageByProductId(productId);
        return ProductImageMapper.INSTANCE.mapProductImageToProductImageDTO(productImage);
    }
}
