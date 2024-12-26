/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class Purchase implements Serializable {

	private static final long serialVersionUID = -6564412536666152947L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String customerId;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	private Order order;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private AddressEntity address;

	@OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private CreditCardEntity creditCard;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime purchaseDate;

}
