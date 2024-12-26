/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.dto;

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
public class CustomerDTO {

	private Long id;

	private String name;

	private String cpf;

	private String email;

	private String tokenId;

	private Profile profile;

	private boolean isSync;

}
