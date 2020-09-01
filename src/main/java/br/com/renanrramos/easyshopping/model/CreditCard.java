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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

	private String creditCardNumber;

	private String ownerName;

	private LocalDate validDate;

	private Integer code;

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
