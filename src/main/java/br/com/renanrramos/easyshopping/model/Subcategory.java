/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Subcategory implements Serializable{

	private static final long serialVersionUID = 1026705649369198665L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	@Column(nullable = false, length = 250)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productCategory_id")
	@Fetch(FetchMode.JOIN)
	private ProductCategory productCategory;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

	public Subcategory() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Subcategory [id=" + id + ", name=" + name + ", productCategory=" + productCategory.getId() + "]";
	}
}
