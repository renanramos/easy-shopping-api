/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 02/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.model.form;

import java.util.Optional;

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;
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
public class StoreForm {

	private String name;
	
	private String registeredNumber;
	
	private String corporateName;
	
	private Long companyId;

	public StoreForm() {
		// Intentionally empty
	}
	
	public StoreForm(String name, String registeredNumber, String corporateName,
			Long companyId) {
		this.name = name;
		this.registeredNumber = registeredNumber;
		this.corporateName = corporateName;
		this.companyId = companyId;
	}

	public static Store converterStoreFormToStore(StoreForm storeForm) {
		return StoreBuilder.builder()
				.withName(storeForm.getName())
				.withCorporateName(storeForm.getCorporateName())
				.withRegisteredNumber(storeForm.getRegisteredNumber())
				.build();
	}

	public static Store converterStoreFormUpdateToStore(StoreForm storeForm, Store currentStore) {
		return StoreBuilder.builder()
				.withName(Optional.ofNullable(storeForm.getName()).orElse(currentStore.getName()))
				.withCorporateName(Optional.ofNullable(storeForm.getCorporateName()).orElse(currentStore.getCorporateName()))
				.withRegisteredNumber(Optional.ofNullable(storeForm.getRegisteredNumber()).orElse(currentStore.getRegisteredNumber()))
				.build();
	}

	@Override
	public String toString() {
		return "StoreForm [name=" + name + ", registeredNumber=" + registeredNumber + ", corporateName=" + corporateName
				+ ", companyId=" + companyId + "]";
	}
}
