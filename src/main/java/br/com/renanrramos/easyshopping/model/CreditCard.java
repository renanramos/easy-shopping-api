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

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCard implements Serializable{

	private static final long serialVersionUID = 4576301775857441140L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String creditCardNumber;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String ownerName;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private LocalDate validDate;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private Integer code;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@Fetch(FetchMode.JOIN)
	private Customer customer;

	public CreditCard() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardNumber=" + creditCardNumber + ", ownerName=" + ownerName + ", validDate="
				+ validDate + ", code=" + code + ", customer=" + customer + "]";
	}

}
