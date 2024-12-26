/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity(name = "Company")
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class CompanyEntity extends UserEntity implements Serializable{

	private static final long serialVersionUID = -5594496999476155657L;

	private String registeredNumber;

	private String phone;

}