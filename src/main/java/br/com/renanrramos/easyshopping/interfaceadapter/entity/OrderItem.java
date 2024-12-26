/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 23/11/2020
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
import javax.persistence.ManyToOne;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@RequiredArgsConstructor
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 6973936402754269388L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private Order order;

	private Long productId;

	private String productName;

	private Integer amount;

	private Double price;

	private Double total;

}
