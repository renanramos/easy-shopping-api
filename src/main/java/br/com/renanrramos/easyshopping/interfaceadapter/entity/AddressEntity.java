/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 11/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity(name = "Address")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class AddressEntity implements Serializable{

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

	private String customerId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;

}
