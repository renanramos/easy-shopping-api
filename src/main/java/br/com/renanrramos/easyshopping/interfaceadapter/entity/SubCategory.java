/**------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.renanrramos.easyshopping.constants.messages.ValidationMessagesConstants;
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
public class SubCategory implements Serializable{

	private static final long serialVersionUID = 1026705649369198665L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 250)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productCategory_id")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private ProductCategory productCategory;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

}
