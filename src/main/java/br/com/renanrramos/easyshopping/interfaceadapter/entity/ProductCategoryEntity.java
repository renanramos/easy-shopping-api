/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 27/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author renan.ramos
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class ProductCategoryEntity implements Serializable {

    private static final long serialVersionUID = -4711255794768433234L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    private String name;

    @OneToMany(targetEntity = SubCategory.class, cascade = CascadeType.ALL, mappedBy = "productCategory", fetch = FetchType.LAZY)
    private List<SubCategory> subcategories;

}
