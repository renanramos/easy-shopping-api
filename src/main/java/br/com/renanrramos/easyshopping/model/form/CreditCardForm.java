/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

	private String validDate;

	private Integer code;

	public CreditCardForm() {
		// Intentionally empty
	}

	public CreditCardForm(String creditCardNumber, String ownerName, 
			String validDate, Integer code) {
		this.creditCardNumber = creditCardNumber;
		this.ownerName = ownerName;
		this.validDate = validDate;
		this.code = code;
	}

	public static CreditCard converterCreditCardFormToCreditCard(CreditCardForm creditCardForm) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return CreditCardBuilder
				.builder()
				.withCreditCardNumber(creditCardForm.getCreditCardNumber())
				.withOwnerName(creditCardForm.getOwnerName())
				.withValidDate(LocalDate.parse(creditCardForm.getValidDate(), dateTimeFormatter))
				.withCode(creditCardForm.getCode())
				.build();
	}

	public static CreditCard converterCreditCardFormUpdateToCreditCard(CreditCardForm creditCardForm, CreditCard currentCreditCard) {
		return CreditCardBuilder
				.builder()
				.withCreditCardNumber(Optional.ofNullable(creditCardForm.getCreditCardNumber()).orElse(currentCreditCard.getCreditCardNumber()))
				.withOwnerName(Optional.ofNullable(creditCardForm.getOwnerName()).orElse(currentCreditCard.getOwnerName()))
				.withValidDate(Optional.ofNullable(LocalDate.parse(creditCardForm.getValidDate())).orElse(LocalDate.parse(creditCardForm.getValidDate())))
				.withCode(Optional.ofNullable(creditCardForm.getCode()).orElse(currentCreditCard.getCode()))
				.build();
	}

	@Override
	public String toString() {
		return "CreditCardForm [creditCardNumber=" + creditCardNumber + ", ownerName=" + ownerName + ", validDate="
				+ validDate + ", code=" + code + "]";
	}
}
