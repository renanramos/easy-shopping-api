/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.time.LocalDate;

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

/**
 * @author renan.ramos
 *
 */
@Entity
public class CreditCard implements Serializable{

	private static final long serialVersionUID = 4576301775857441140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String creditCardNumber;

	@NotBlank
	private String ownerName;

	@NotNull
	private LocalDate validDate;

	@NotNull
	private Integer code;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@Fetch(FetchMode.JOIN)
	private Customer customer;

	public CreditCard() {
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public LocalDate getValidDate() {
		return validDate;
	}

	public Integer getCode() {
		return code;
	}

	public Customer getCustomer() {
		return customer != null ? customer : new Customer();
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setValidDate(LocalDate validDate) {
		this.validDate = validDate;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardNumber=" + creditCardNumber + ", ownerName=" + ownerName + ", validDate="
				+ validDate + ", code=" + code + ", customer=" + customer + "]";
	}

}
