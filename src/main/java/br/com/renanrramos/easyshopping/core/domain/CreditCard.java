/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import java.time.LocalDate;

/**
 * @author renan.ramos
 *
 */
public class CreditCard {
	private Long id;
	private String creditCardNumber;
	private String ownerName;
	private LocalDate validDate;
	private Integer code;
	private String customerId;
	private Purchase purchase;

	public CreditCard() {
		// Intentionally empty
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public LocalDate getValidDate() {
		return validDate;
	}

	public void setValidDate(LocalDate validDate) {
		this.validDate = validDate;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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
		return "CreditCard{" +
				"id=" + id +
				", creditCardNumber='" + creditCardNumber + '\'' +
				", ownerName='" + ownerName + '\'' +
				", validDate=" + validDate +
				", code=" + code +
				", customerId='" + customerId + '\'' +
				", purchase=" + purchase +
				'}';
	}
}
