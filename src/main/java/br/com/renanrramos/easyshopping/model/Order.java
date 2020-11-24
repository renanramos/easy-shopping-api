/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "`order`")
public class Order implements Serializable {

	private static final long serialVersionUID = -6015194357896415019L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	private String orderNumber;

	private String customerId;

	@OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItem> items = new ArrayList<>();

	public Order() {
		// Intentionally empty
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + "]";
	}
}
