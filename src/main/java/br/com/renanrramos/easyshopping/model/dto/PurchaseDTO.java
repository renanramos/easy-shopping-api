/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 02/12/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.Purchase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author renan.ramos
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseDTO {

	private Long id;

	private String customerId;

	private Order order;

	private Address address;

	private CreditCard creditCard;

	private LocalDateTime date;

	public PurchaseDTO() {
		// Intentionally empty
	}

	public PurchaseDTO(Purchase purchase) {
		this.id = purchase.getId();
		this.customerId = purchase.getCustomerId();
		this.order = purchase.getOrder();
		this.address = purchase.getAddress();
		this.creditCard = purchase.getCreditCard();
		this.date = purchase.getDate();
	}

	public static List<PurchaseDTO> convertPurchaseListToPurchaseDTOList(List<Purchase> purchases) {
		return purchases.stream().map(PurchaseDTO::new).collect(Collectors.toList());
	}

	public static PurchaseDTO convertPurchaseToPurchaseDTO(Purchase purchase) {
		return new PurchaseDTO(purchase);
	}

	@Override
	public String toString() {
		return "PurchaseDTO [id=" + id + ", customerId=" + customerId + ", order=" + order + ", address=" + address
				+ ", creditCard=" + creditCard + ", date=" + date + "]";
	}
}
