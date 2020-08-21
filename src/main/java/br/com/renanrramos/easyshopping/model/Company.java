/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Pattern;

import br.com.renanrramos.easyshopping.constants.messages.PatternValidationConstants;
import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
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
@PrimaryKeyJoinColumn(name="id")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Company extends User implements Serializable{

	private static final long serialVersionUID = -5594496999476155657L;
	
	@Pattern(regexp = PatternValidationConstants.REGISTERED_NUMBER_PATTERN, message = ValidationMessagesConstants.INVALID_REGISTERED_NUMBER_FORMAT)
	private String registeredNumber;

	private String phone;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY)
	private List<Store> stores = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<>();

	public Company() {		
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Company [registeredNumber=" + registeredNumber + "]";
	}
}
