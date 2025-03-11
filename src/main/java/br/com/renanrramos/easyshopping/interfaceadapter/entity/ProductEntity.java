/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
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
import java.util.Set;

/**
 * @author renan.ramos
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class ProductEntity implements Serializable {

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

    @OneToMany(targetEntity = ProductImageEntity.class, cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER)
    private Set<ProductImageEntity> images;

    private boolean isPublished;

}
