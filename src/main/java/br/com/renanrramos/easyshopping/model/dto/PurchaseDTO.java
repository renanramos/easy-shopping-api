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

	private Long orderId;

	private Long addressId;

	private Long creditCardId;

	private LocalDateTime date;

	public PurchaseDTO() {
		// Intentionally empty
	}

	public PurchaseDTO(Purchase purchase) {
		this.id = purchase.getId();
		this.customerId = purchase.getCustomerId();
		this.orderId = purchase.getOrder().getId();
		this.addressId = purchase.getAddress().getId();
		this.creditCardId = purchase.getCreditCard().getId();
		this.date = purchase.getPurchaseDate();
	}

	public static List<PurchaseDTO> convertPurchaseListToPurchaseDTOList(List<Purchase> purchases) {
		return purchases.stream().map(PurchaseDTO::new).collect(Collectors.toList());
	}

	public static PurchaseDTO convertPurchaseToPurchaseDTO(Purchase purchase) {
		return new PurchaseDTO(purchase);
	}

	@Override
	public String toString() {
		return "PurchaseDTO [id=" + id + ", customerId=" + customerId + ", order=" + orderId + ", address=" + addressId
				+ ", creditCard=" + creditCardId + ", date=" + date + "]";
	}
}
