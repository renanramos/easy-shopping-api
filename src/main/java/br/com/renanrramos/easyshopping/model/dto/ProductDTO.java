/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renanrramos.easyshopping.config.util.EasyShoppingUtils;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductImage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private double price;

	private Long subcategoryId;
	
	private String subcategoryName;
	
	private Long storeId;

	private Set<ProductImage> productImages;

	@JsonIgnore
	private EasyShoppingUtils utils = new EasyShoppingUtils();

	public ProductDTO() {
		// Intentionally empty
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.subcategoryName = product.getSubcategory().getName();
		this.storeId = product.getStore().getId();
		this.subcategoryId = product.getSubcategory().getId();
		this.productImages = product.getImages().stream().map(productImage -> descompressImages(productImage)).collect(Collectors.toSet());
	}

	public static List<ProductDTO> converterProductListToProductDTOList(List<Product> products) {
		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
	}
	
	public static ProductDTO convertProductToProductDTO(Product product) {
		return new ProductDTO(product);
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", productCategoryId=" + subcategoryId + ", subcategoryName=" + subcategoryName
				+ ", storeId=" + storeId + "]";
	}

	private ProductImage descompressImages(ProductImage productImage) {
		byte[] picture = productImage.getPicture();
		productImage.setPicture(utils.decompressImageBytes(picture));
		return productImage;
	}
}
