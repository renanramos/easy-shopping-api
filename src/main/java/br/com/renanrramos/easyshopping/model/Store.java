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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

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
public class Store implements Serializable{

	private static final long serialVersionUID = 979835710158008524L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;
	
	@NotBlank
	@Column(nullable = false, length = 50)
	private String name;
	
	@NotBlank
	@Column(nullable = false, length = 50)
	private String registeredNumber;
	
	@NotBlank
	@Column(nullable = false, length = 250)
	private String corporateName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
	private List<Product> products = new ArrayList<>();
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private Company company;

	public Store() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName="
				+ corporateName + ", products=" + products + ", company=" + company + "]";
	}
}
