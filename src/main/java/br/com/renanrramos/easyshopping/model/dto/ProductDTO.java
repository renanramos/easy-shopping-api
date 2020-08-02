/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;
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
	
	private String productCategoryName;
	
	private Store store;
	
	public ProductDTO() {
		// Intentionally empty
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.productCategoryName = product.getProductCategory().getName();
		this.store = getStoreInfo(product.getStore());
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
				+ ", productCategoryName=" + productCategoryName + ", store=" + store + "]";
	}

	private static Store getStoreInfo(Store store) {
		return StoreBuilder.builder()
				.withCorporateName(store.getCorporateName())
				.withName(store.getName())
				.withRegisteredNumber(store.getRegisteredNumber())
				.build();
	}
	
}
