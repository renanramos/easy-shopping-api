/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 10/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
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

	private String productName;

	private Double maxAmount;

	private Double minAmount;

	private Integer currentAmount;

}
