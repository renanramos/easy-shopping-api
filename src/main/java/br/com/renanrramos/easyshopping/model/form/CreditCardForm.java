/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.time.LocalDate;

import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.builder.CreditCardBuilder;
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
public class CreditCardForm {
	
	private String creditCardNumber;

	private String ownerName;

	private LocalDate validDate;

	private Integer code;

	private Long customerId;

	public CreditCardForm() {
		// Intentionally empty
	}

	public CreditCardForm(String creditCardNumber, String ownerName, 
			LocalDate validDate, Integer code, Long customerId) {
		this.creditCardNumber = creditCardNumber;
		this.ownerName = ownerName;
		this.validDate = validDate;
		this.code = code;
		this.customerId = customerId;
	}

	public static CreditCard converterCreditCardFormToCreditCard(CreditCardForm creditCardForm) {
		return CreditCardBuilder
				.builder()
				.withCreditCardNumber(creditCardForm.getCreditCardNumber())
				.withOwnerName(creditCardForm.getOwnerName())
				.withValidDate(creditCardForm.getValidDate())
				.withCode(creditCardForm.getCode())
				.build();
	}
	
	@Override
	public String toString() {
		return "CreditCardForm [creditCardNumber=" + creditCardNumber + ", ownerName=" + ownerName + ", validDate="
				+ validDate + ", code=" + code + "]";
	}
}
