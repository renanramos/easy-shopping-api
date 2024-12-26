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

	private String streetName;

	private String district;

	private Long number;

	private String cep;

	private String state;

	private String city;

	private String customerId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;

}
