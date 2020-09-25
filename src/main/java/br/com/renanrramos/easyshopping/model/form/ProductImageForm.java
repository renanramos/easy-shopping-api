/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import br.com.renanrramos.easyshopping.model.ProductImage;
import br.com.renanrramos.easyshopping.model.builder.ProductImageBuilder;
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
public class ProductImageForm {

	private String description;

	private Long productId;

	private boolean isCoverImage;

	public ProductImageForm() {
		// Intentionally empty
	}

	public ProductImageForm(String description, Long productId, boolean isCoverImage) {
		this.description = description;
		this.productId = productId;
		this.isCoverImage = isCoverImage;
	}

	public static ProductImage convertProductImageFormToProductImate(ProductImageForm productImageForm) {
		return ProductImageBuilder.builder()
				.withDescription(productImageForm.getDescription())
				.withCoverImage(productImageForm.isCoverImage())
				.build();
	}
	
}
