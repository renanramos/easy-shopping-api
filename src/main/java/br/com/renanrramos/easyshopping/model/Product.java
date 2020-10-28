/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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

	@Column(nullable = false, length = 50)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@Column(nullable = false, length = 250)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String description;

	private double price;
	
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private Subcategory subcategory;
	
	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	private String companyId;

	@OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
	private Set<ProductImage> images;  

	public Product() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", subcategory=" + subcategory + ", store=" + store + "]";
	}
}
