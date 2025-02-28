/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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
public class Store implements Serializable {

    private static final long serialVersionUID = 979835710158008524L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private String registeredNumber;

    @Column(nullable = false, length = 250)
    private String corporateName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "store")
    private List<Stock> stocks = new ArrayList<>();

    private String tokenId;

}
