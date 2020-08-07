/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.builder.CompanyBuilder;
import br.com.renanrramos.easyshopping.model.builder.ProductBuilder;
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
public class StoreDTO {

	private Long id;
	
	private String name;
	
	private String registeredNumber;
	
	private String corporateName;

	private Long companyId;
	
	public StoreDTO() {
		// Intentionally empty
	}
	
	public StoreDTO(Store store) {
		this.id = store.getId();
		this.name = store.getName();
		this.registeredNumber = store.getRegisteredNumber();
		this.corporateName = store.getCorporateName();
		this.companyId = store.getCompany().getId();
	}

	public static List<StoreDTO> converterStoreListToStoreDTOList(List<Store> stores) {
		return stores.stream().map(StoreDTO::new).collect(Collectors.toList());
	}
	
	public static StoreDTO converterStoreToStoreDTO(Store store) {
		return new StoreDTO(store);
	}
	
	private static Product getProductInfo(Product product) {
		return ProductBuilder.builder()
				.withDescription(product.getDescription())
				.withName(product.getName())
				.withPrice(product.getPrice())
				.withProductCategory(product.getProductCategory())
				.build();
	}
}
