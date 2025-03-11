/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author renan.ramos
 */
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "`order`")
@ToString
@RequiredArgsConstructor
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = -6015194357896415019L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String customerId;

    private boolean finished;

    @OneToMany(targetEntity = OrderItemEntity.class, cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItemEntity> items = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

}
