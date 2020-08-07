/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.CreditCard;
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
public class CreditCardDTO {

	private Long id;
	
	private String creditCardNumber;

	private String ownerName;

	private LocalDate validDate;

	private Integer code;

	private Long customerId;

	public CreditCardDTO() {
		// Intentionally empty
	}

	public CreditCardDTO(CreditCard creditCard) {
		this.id = creditCard.getId();
		this.creditCardNumber = creditCard.getCreditCardNumber();
		this.ownerName = creditCard.getOwnerName();
		this.validDate = creditCard.getValidDate();
		this.code = creditCard.getCode();
		this.customerId = creditCard.getCustomer().getId();
	}

	public static CreditCardDTO converterCreditCardToCreditCardDTO(CreditCard creditCard) {
		return new CreditCardDTO(creditCard);
	}

	public static List<CreditCardDTO> converterCreditCardListToCreditCardDTOList(List<CreditCard> creditCards) {
		return creditCards.stream().map(CreditCardDTO::new).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "CreditCardDTO [id=" + id + ", creditCardNumber=" + creditCardNumber + ", ownerName=" + ownerName
				+ ", validDate=" + validDate + ", code=" + code + ", customerId=" + customerId + "]";
	}
}
