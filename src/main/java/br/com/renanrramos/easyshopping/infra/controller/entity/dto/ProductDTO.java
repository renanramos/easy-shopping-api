/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductImage;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDTO {

	private Long id;

	private String name;

	private String description;

	private double price;

	private Long subCategoryId;

	private String subCategoryName;

	private Long storeId;

	private Set<ProductImage> productImages;

	private boolean isProductPublished;

	public ProductDTO() {
		// Intentionally empty
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.subCategoryName = product.getSubCategory().getName();
		this.storeId = product.getStore().getId();
		this.subCategoryId = product.getSubCategory().getId();
		this.productImages = Optional.ofNullable(product.getImages()).orElse(new HashSet<>());
		this.isProductPublished = product.isPublished();
	}

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
