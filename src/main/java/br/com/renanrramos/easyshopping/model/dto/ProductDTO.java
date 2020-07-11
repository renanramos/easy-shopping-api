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
import br.com.renanrramos.easyshopping.model.ProductCategory;
import br.com.renanrramos.easyshopping.model.Store;

/**
 * @author renan.ramos
 *
 */
public class ProductDTO {

	private Long id;
	
	private String name;
	
	private String description;
	
	private double price;
	
	private String productCategoryName;
	
	private String storeName;
	
	public ProductDTO() {
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.productCategoryName = product.getProductCategory().getName();
		this.storeName = product.getStore() == null ? "No store found" : product.getStore().getCorporateName();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setProductCategory(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public void setStore(String storeName) {
		this.storeName = storeName;
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
				+ ", productCategoryName=" + productCategoryName + ", storeName=" + storeName + "]";
	}

}
