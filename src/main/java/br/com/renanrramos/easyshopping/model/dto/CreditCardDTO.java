/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCardDTO {

	private Long id;
	
	private String creditCardNumber;

	private String ownerName;

	private LocalDate validDate;

	private Integer code;

	private String customerId;

	public CreditCardDTO() {
		// Intentionally empty
	}
}
