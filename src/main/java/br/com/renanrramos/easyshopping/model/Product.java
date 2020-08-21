/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product implements Serializable{

	private static final long serialVersionUID = -8837444544799506973L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;
	
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	@Column(nullable = false, length = 50)
	private String name;
	
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	@Column(nullable = false, length = 250)
	private String description;
	
	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productSubcategory_id")
	@Fetch(FetchMode.JOIN)
	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private Subcategory productSubcategory;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	public Product() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", productSubcategory=" + productSubcategory + ", store=" + store + "]";
	}
}
