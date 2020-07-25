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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.sql.rowset.serial.SerialArray;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.renanrramos.easyshopping.enums.Profile;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author renan.ramos
 *
 */
@Entity
@PrimaryKeyJoinColumn(name="id")
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
	
	@OneToMany(targetEntity = Store.class, cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
	private Set<Store> stores;

	public Company() {		
	}

	public String getRegisteredNumber() {
		return registeredNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((registeredNumber == null) ? 0 : registeredNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (profile != other.profile)
			return false;
		if (registeredNumber == null) {
			if (other.registeredNumber != null)
				return false;
		} else if (!registeredNumber.equals(other.registeredNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [registeredNumber=" + registeredNumber + ", email=" + email
				+ ", phone=" + phone + ", profile=" + profile + ", stores=" + stores + "]";
	}

}
