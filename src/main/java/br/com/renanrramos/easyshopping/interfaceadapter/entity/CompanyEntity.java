/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 26/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Entity(name = "Company")
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class CompanyEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = -5594496999476155657L;

    private String registeredNumber;

    private String phone;

}