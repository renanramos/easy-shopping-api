/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 08/08/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class SubCategory implements Serializable {

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
    private ProductCategoryEntity productCategory;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();

}
