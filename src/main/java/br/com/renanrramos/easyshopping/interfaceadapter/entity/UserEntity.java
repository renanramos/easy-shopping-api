/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.interfaceadapter.entity;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@ToString
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -234475925678811197L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(length = 250)
    private String email;

    @ApiModelProperty(hidden = true)
    @Enumerated(EnumType.STRING)
    private Profile profile;

    @Column(length = 250)
    private String tokenId;

    private boolean isSync;
}
