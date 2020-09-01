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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Email
	@Column(nullable = false, length = 250)
	private String email;

	@ApiModelProperty(hidden = true)
	@Enumerated(EnumType.STRING)
	private Profile profile;

	@JsonIgnore
	@Column(nullable = false, length = 250)
	private String password;

	public User() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}	
}
