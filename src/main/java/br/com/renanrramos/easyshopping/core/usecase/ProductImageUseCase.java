package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductImageDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductImageForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;

public interface ProductImageUseCase {

    ProductImageDTO save(final ProductImageForm productImageForm);

    PageResponse<ProductImageDTO> findAll(final ParametersRequest parametersRequest);

    ProductImageDTO findById(final Long productImageId);

    ProductImageDTO update(final ProductImageForm productImageForm, final Long productImageId);

    void remove(final Long productImageId);

    ProductImageDTO getImageByProductId(final Long productId);
}
