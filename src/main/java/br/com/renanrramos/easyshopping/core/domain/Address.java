/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

/**
 * @author renan.ramos
 *
 */
public class Address {

	private Long id;
	private String streetName;
	private String district;
	private Long number;
	private String cep;
	private String state;
	private String city;
	private String customerId;
	private Purchase purchase;

	public Address() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", streetName=" + streetName + ", district=" + district + ", number=" + number
				+ ", cep=" + cep + ", state=" + state + ", city=" + city + ", customer=" + customerId + "]";
	}

}
