/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import br.com.renanrramos.easyshopping.enums.AuthProvider;
import br.com.renanrramos.easyshopping.enums.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Serializable{
 
	private static final long serialVersionUID = -234475925678811197L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(length = 50)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String name;

	@Email
	@Column(length = 250)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String email;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile;

	@JsonIgnore
	@Column(length = 250)
	@NotBlank(message = ValidationMessagesConstants.EMPTY_FIELD)
	private String password;

	@JsonIgnore
	@Column(nullable = false)
	private boolean isActive;

	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	public User() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}	
}
