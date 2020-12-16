/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Purchase implements Serializable {

	private static final long serialVersionUID = -6564412536666152947L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	private String customerId;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	private Order order;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Address address;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private CreditCard creditCard;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime purchaseDate;

	public Purchase() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", customerId=" + customerId + ", order=" + order + ", address=" + address
				+ ", creditCard=" + creditCard + ", date=" + purchaseDate + "]";
	}
}
