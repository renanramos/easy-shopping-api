/**------------------------------------------------------------
 * Project: easy-shopping
 *
 * Creator: renan.ramos - 26/11/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.controller.rest;

import br.com.renanrramos.easyshopping.enums.Profile;
import br.com.renanrramos.easyshopping.model.Address;
import br.com.renanrramos.easyshopping.model.Company;
import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.builder.CreditCardBuilder;
import br.com.renanrramos.easyshopping.model.builder.OrderBuilder;
import br.com.renanrramos.easyshopping.model.builder.OrderItemBuilder;
import br.com.renanrramos.easyshopping.model.builder.ProductBuilder;
import br.com.renanrramos.easyshopping.model.form.CreditCardForm;
import br.com.renanrramos.easyshopping.model.form.OrderItemForm;

/**
 * @author renan.ramos
 *
 */
public class EasyShoppingUtil {

	public static Address getAddress() {
		Address address = new Address();
		address.setId(1L);
		address.setCep("cep");
		address.setDistrict("district");
		address.setNumber(123L);
		address.setState("state");
		address.setStreetName("streetName");
		address.setCustomerId("customerId");
		return address;
	}

	public static Company getCompanyInstance() {
		Company company = new Company();
		company.setId(1L);
		company.setName("Teste");
		company.setPhone("13213321");
		company.setProfile(Profile.COMPANY);
		company.setRegisteredNumber("1315adsd5");
		company.setEmail("company@mail.com");
		return company;
	}

	public static CreditCard getCreditCardInstance(Long id) {
		return CreditCardBuilder.builder().withId(id).withCode(123).withCreditCardNumber("1234 1234 1234 1234")
				.withOwnerName("OWNER NAME").build();
	}

	public static CreditCardForm getCreditCardForm() {
		CreditCardForm creditCard = new CreditCardForm();
		creditCard.setCode(123);
		creditCard.setCreditCardNumber("1234 1234 1234 1234");
		creditCard.setOwnerName("OWNER NAME");
		creditCard.setValidDate("2031-01-02");
		return creditCard;
	}

	public static OrderItem getOrderItemInstance() {
		return OrderItemBuilder.builder().withAmount(2).withPrice(20.0).withProductId(1L).withTotal(40.0)
				.withOrder(getOrderInstance()).build();
	}

	public static OrderItemForm getOrderItemFormInstance() {
		return new OrderItemForm(1L, 1L, 2, 3.0, 6.0);
	}

	public static Order getOrderInstance() {
		return OrderBuilder.builder().withCustomerId("customerId").withOrderNumber("orderNumber").build();
	}

	public static Product getProductInstance() {
		return ProductBuilder.builder().withCompany("companyId").withDescription("description").withId(1L)
				.withName("productName").withPrice(20.0).build();
	}
}
