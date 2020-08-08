/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 08/08/2020
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class SubCategory implements Serializable{

	private static final long serialVersionUID = 1026705649369198665L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 250)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productCategory_id")
	@Fetch(FetchMode.JOIN)
	@NotNull
	private ProductCategory productCategory;

	public SubCategory() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "SubCategory [id=" + id + ", name=" + name + ", productCategory=" + productCategory.getId() + "]";
	}
}