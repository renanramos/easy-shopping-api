package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.Product;
import br.com.renanrramos.easyshopping.core.gateway.ProductGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.ProductDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.ProductForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductGateway productGateway;

    @Override
    public ProductDTO save(final ProductForm productForm) {
        final Product product = ProductMapper.INSTANCE.mapProductFormToProduct(productForm);
        return ProductMapper.INSTANCE.mapProductToProductDTO(productGateway.save(product));
    }

    @Override
    public PageResponse<ProductDTO> findAll(final ParametersRequest parametersRequest) {
        final Page<Product> productPage = productGateway.findAll(parametersRequest);
        return new PageResponse<>(productPage.getTotalElements(), productPage.getTotalPages(),
                ProductMapper.INSTANCE.mapProductListToProductDTOList(productPage.toList()));
    }

    @Override
    public ProductDTO findById(final Long productId) {
        return ProductMapper.INSTANCE.mapProductToProductDTO(productGateway.findById(productId));
    }

    @Override
    public ProductDTO update(final ProductForm productForm, final Long productId) {
        final Product product =
                productGateway.update(ProductMapper.INSTANCE.mapProductFormToProduct(productForm), productId);
        return ProductMapper.INSTANCE.mapProductToProductDTO(product);
    }

    @Override
    public void remove(final Long productId) {
        productGateway.remove(productId);
    }

    @Override
    public PageResponse<ProductDTO> findProductByStoreId(final ParametersRequest parametersRequest, final Long storeId) {
        final Page<Product> productPage = productGateway.findProductByStoreId(parametersRequest, storeId);
        return buildProductDTOPageResponse(productPage);
    }

    @Override
    public PageResponse<ProductDTO> getProductByName(final ParametersRequest parametersRequest, final String name) {
        final Page<Product> productPage = productGateway.getProductByName(parametersRequest, name);
        return buildProductDTOPageResponse(productPage);
    }

    @Override
    public PageResponse<ProductDTO> findProductByCompanyId(final ParametersRequest parametersRequest,
                                                           final String companyId) {
        final Page<Product> productPage =
                productGateway.findProductByCompanyId(parametersRequest, companyId);
        return buildProductDTOPageResponse(productPage);
    }

    @Override
    public PageResponse<ProductDTO> findProductBySubCategoryId(final ParametersRequest parametersRequest,
                                                               final Long subCategoryId) {
        final Page<Product> productPage = productGateway.findProductBySubCategoryId(parametersRequest, subCategoryId);
        return buildProductDTOPageResponse(productPage);
    }

    @Override
    public PageResponse<ProductDTO> getProductByCompanyIdAndName(final ParametersRequest parametersRequest,
                                                                 final String tokenId,
                                                                 final String name) {
        final Page<Product> productPage = productGateway.getProductByCompanyIdAndName(parametersRequest, tokenId, name);
        return buildProductDTOPageResponse(productPage);
    }

    private static PageResponse<ProductDTO> buildProductDTOPageResponse(final Page<Product> productPage) {
        return new PageResponse<>(productPage.getTotalElements(), productPage.getTotalPages(),
                ProductMapper.INSTANCE.mapProductListToProductDTOList(productPage.getContent()));
    }
}
