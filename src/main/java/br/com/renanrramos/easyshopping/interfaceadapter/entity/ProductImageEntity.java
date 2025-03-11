/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 23/09/2020
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

/**
 * @author renan.ramos
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class ProductImageEntity implements Serializable {

    private static final long serialVersionUID = 5427495329867275306L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column
    private String description;

    @Column(length = 99999)
    private byte[] picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private ProductEntity product;

    private boolean isCoverImage;

}
