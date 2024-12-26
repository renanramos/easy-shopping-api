/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 30/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author renan.ramos
 *
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class CompanyDTO {

	private Long id;

	private String name;

	private String registeredNumber;

	private String email;

	private String phone;

	private Profile profile;

	private boolean isSync;

	private String tokenId;

}
