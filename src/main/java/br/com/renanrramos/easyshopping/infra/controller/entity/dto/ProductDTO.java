/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import br.com.renanrramos.easyshopping.interfaceadapter.entity.ProductImageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * @author renan.ramos
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private double price;

    private Long subCategoryId;

    private String subCategoryName;

    private Long storeId;

    private Set<ProductImageEntity> productImages;

    private boolean isProductPublished;

//	public static List<ProductDTO> converterProductListToProductDTOList(List<Product> products) {
//		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
//	}
//
//	public static List<ProductDTO> converterPublishedProductListToProductDTOList(List<Product> products) {
//		return products.stream().filter(product -> product.isPublished()).map(ProductDTO::new)
//				.collect(Collectors.toList());
//	}
//	public static ProductDTO convertProductToProductDTO(Product product) {
//		return new ProductDTO(product);
//	}
//
//	@Override
//	public String toString() {
//		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
//				+ ", productCategoryId=" + subcategoryId + ", subcategoryName=" + subcategoryName
//				+ ", storeId=" + storeId + "]";
//	}
}
