/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author renan.ramos
 *
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class Product implements Serializable{

	private static final long serialVersionUID = -8837444544799506973L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 250)
	private String description;

	private double price;

	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private SubCategory subCategory;

	@ManyToOne
	@JoinColumn(name = "store_id")
	private Store store;

	private String companyId;

	@OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
	private Set<ProductImage> images;

	private boolean isPublished;

}
