/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.builder;

import java.time.LocalDate;

import br.com.renanrramos.easyshopping.model.CreditCard;

/**
 * @author renan.ramos
 *
 */
public class CreditCardBuilder {

	private CreditCard creditCard;

	public CreditCardBuilder() {
		this.creditCard = new CreditCard();
	}

	public static CreditCardBuilder builder() {
		return new CreditCardBuilder();
	}

	public CreditCardBuilder withId(Long id) {
		this.creditCard.setId(id);
		return this;
	}

	public CreditCardBuilder withCreditCardNumber(String creditCardNumber) {
		this.creditCard.setCreditCardNumber(creditCardNumber);
		return this;
	}

	public CreditCardBuilder withOwnerName(String ownerName) {
		this.creditCard.setOwnerName(ownerName);
		return this;
	}

	public CreditCardBuilder withValidDate(LocalDate validDate) {
		this.creditCard.setValidDate(validDate);
		return this;
	}

	public CreditCardBuilder withCode(Integer code) {
		this.creditCard.setCode(code);
		return this;
	}

	public CreditCard build() {
		return this.creditCard;
	}
}
