/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

/**
 * @author renan.ramos
 *
 */
@Entity
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private List<Product> products;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRegisteredNumber() {
		return registeredNumber;
	}
	
	public String getCorporateName() {
		return corporateName;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRegisteredNumber(String registeredNumber) {
		this.registeredNumber = registeredNumber;
	}
	
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registeredNumber == null) ? 0 : registeredNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((corporateName == null) ? 0 : corporateName.hashCode());
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
		Store other = (Store) obj;
		if (registeredNumber == null) {
			if (other.registeredNumber != null)
				return false;
		} else if (!registeredNumber.equals(other.registeredNumber))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (corporateName == null) {
			if (other.corporateName != null)
				return false;
		} else if (!corporateName.equals(other.corporateName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName=" + corporateName + ", products="
				+ products + "]";
	}
		
}
