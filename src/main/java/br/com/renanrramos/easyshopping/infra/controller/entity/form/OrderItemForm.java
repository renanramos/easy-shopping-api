/**
 * ------------------------------------------------------------
 * Project: easy-shopping
 * <p>
 * Creator: renan.ramos - 23/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.infra.controller.entity.form;

import br.com.renanrramos.easyshopping.core.domain.constants.ExceptionConstantMessages;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author renan.ramos
 */
@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class OrderItemForm {

    @NotNull(message = ExceptionConstantMessages.ORDER_ID_NOT_FOUND_ON_REQUEST)
    private Long orderId;

    @NotNull(message = ExceptionConstantMessages.PRODUCT_ID_NOT_FOUND_ON_REQUEST)
    private Long productId;

    private String productName;

    private Integer amount;

    private Double price;

    private Double total;

}
