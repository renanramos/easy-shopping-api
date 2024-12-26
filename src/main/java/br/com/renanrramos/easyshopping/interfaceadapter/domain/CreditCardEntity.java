/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 01/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@Entity(name = "CreditCard")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCardEntity implements Serializable{

	private static final long serialVersionUID = 4576301775857441140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String creditCardNumber;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String ownerName;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDate validDate;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private Integer code;

	private String customerId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;

	public CreditCardEntity() {
		// Intentionally empty
	}
}
