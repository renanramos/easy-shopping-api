/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL, mappedBy = "product")
	@Fetch(FetchMode.JOIN)
	private List<ProductImage> images = new ArrayList<>();  

	public Product() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", subcategory=" + subcategory + ", store=" + store + "]";
	}
}
