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
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

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
public class Company extends User implements Serializable{

	private static final long serialVersionUID = -5594496999476155657L;

	private String registeredNumber;

	private String phone;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
	private List<Store> stores = new ArrayList<>();

	public Company() {		
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Company [registeredNumber=" + registeredNumber + "]";
	}
}
