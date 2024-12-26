/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "`order`")
@ToString
@RequiredArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = -6015194357896415019L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String orderNumber;

	private String customerId;

	private boolean finished;

	@OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderItem> items = new ArrayList<>();

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;

}
