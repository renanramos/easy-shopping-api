/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renanrramos.easyshopping.config.util.EasyShoppingUtils;
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
public class ProductImageDTO {

	@JsonIgnore
	private EasyShoppingUtils utils = new EasyShoppingUtils();
	
	private Long id;

	private String description;

	private byte[] picture;

	private Long productId;

	public ProductImageDTO() {
		// Intentionally empty
	}

	public ProductImageDTO(ProductImage productImage) {
		this.id = productImage.getId();
		this.description = productImage.getDescription();
		this.picture = Optional.ofNullable(utils.decompressImageBytes(productImage.getPicture())).orElse(null);
		this.productId = productImage.getProduct().getId();
	}

	public static List<ProductImageDTO> converterProducImageListToProductImageDTOList(List<ProductImage> productImages) {
		return productImages.stream().map(ProductImageDTO::new).collect(Collectors.toList());
	}

	public static ProductImageDTO converterProductImageToProductImageDTO(ProductImage productImage) {
		return new ProductImageDTO(productImage);
	}

	@Override
	public String toString() {
		return "ProductImageDTO [id=" + id + ", description=" + description + ", picture=" + Arrays.toString(picture)
				+ ", productId=" + productId + "]";
	}
}
