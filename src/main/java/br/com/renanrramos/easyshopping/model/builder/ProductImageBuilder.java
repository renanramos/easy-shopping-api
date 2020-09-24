/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.ProductImage;

/**
 * @author renan.ramos
 *
 */
public class ProductImageBuilder {

	private ProductImage productImage;

	private ProductImageBuilder() {
		this.productImage = new ProductImage();
	}

	public static ProductImageBuilder builder() {
		return new ProductImageBuilder();
	}

	public ProductImageBuilder withDescription(String description) {
		this.productImage.setDescription(description);
		return this;
	}

	public ProductImageBuilder withPicture(byte[] picture) {
		this.productImage.setPicture(picture);
		return this;
	}

	public ProductImageBuilder withProduct(Product product) {
		this.productImage.setProduct(product);
		return this;
	}

	public ProductImage build() {
		return this.productImage;
	}
}
