/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class StockItem implements Serializable {

	private static final long serialVersionUID = 6415687420314987028L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "stock_id")
	private Stock stock;

	private Long productId;

	private Double maxAmount;

	private Double minAmount;

	private Double currentAmount;

	@Override
	public String toString() {
		return "StockItem [id=" + id + ", stock=" + stock + ", productId=" + productId + ", maxAmount=" + maxAmount
				+ ", minAmount=" + minAmount + ", currentAmount=" + currentAmount + "]";
	}
}
