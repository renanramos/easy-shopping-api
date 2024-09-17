/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 23/09/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author renan.ramos
 *
 */
public class ProductImage {

	private Long id;
	private String description;
	private byte[] picture;
	private Product product;
	private boolean isCoverImage;

	public ProductImage() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isCoverImage() {
		return isCoverImage;
	}

	public void setCoverImage(boolean coverImage) {
		isCoverImage = coverImage;
	}

	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", description=" + description + ", picture=" + Arrays.toString(picture)
				+ ", product=" + product + "]";
	}
}
