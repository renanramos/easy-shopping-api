/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 24/06/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.core.domain;

import br.com.renanrramos.easyshopping.core.domain.enums.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private Profile profile;
    private String tokenId;
    private boolean isSync;

}
