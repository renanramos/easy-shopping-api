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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.renanrramos.easyshopping.enums.Profile;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Company extends User implements Serializable{

	private static final long serialVersionUID = -5594496999476155657L;
	
	@NotBlank
	private String registeredNumber;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String phone;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
	private List<Store> stores = new ArrayList<>();

	public Company() {		
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Company [registeredNumber=" + registeredNumber + ", email=" + email + ", phone=" + phone + ", profile="
				+ profile + ", stores=" + stores + "]";
	}
}
