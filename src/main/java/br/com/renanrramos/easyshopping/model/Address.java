/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
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
import io.swagger.annotations.ApiModelProperty;
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
public class Address implements Serializable{

	private static final long serialVersionUID = 1130436355437175562L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String streetName;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String district;

	@NotNull(message = ValidationMessagesConstants.EMPTY_FIELD)
	private Long number;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String cep;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String state;

	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String city;

	private String userTokenId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	@Fetch(FetchMode.JOIN)
	private Customer customer;
	
	public Address() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", streetName=" + streetName + ", district=" + district + ", number=" + number
				+ ", cep=" + cep + ", state=" + state + ", city=" + city + ", customer=" + customer + "]";
	}
}
