/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class ProductCategory implements Serializable{
 
	private static final long serialVersionUID = -4711255794768433234L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	@Column(nullable = false, length = 250)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@OneToMany(targetEntity = SubCategory.class, cascade = CascadeType.ALL, mappedBy = "productCategory", fetch = FetchType.LAZY)
	private List<SubCategory> subcategories;
	
	public ProductCategory() {
		// Intentionally empty
	}
	
	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + "]";
	}
}
