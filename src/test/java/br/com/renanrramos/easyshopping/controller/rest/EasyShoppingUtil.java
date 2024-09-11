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
import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.Order;
import br.com.renanrramos.easyshopping.model.OrderItem;
import br.com.renanrramos.easyshopping.model.Product;
import br.com.renanrramos.easyshopping.model.Purchase;
import br.com.renanrramos.easyshopping.model.Stock;
import br.com.renanrramos.easyshopping.model.StockItem;
import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.model.User;
import br.com.renanrramos.easyshopping.model.builder.OrderBuilder;
import br.com.renanrramos.easyshopping.model.builder.OrderItemBuilder;
import br.com.renanrramos.easyshopping.model.builder.ProductBuilder;
import br.com.renanrramos.easyshopping.model.builder.PurchaseBuilder;
import br.com.renanrramos.easyshopping.model.builder.StockBuilder;
import br.com.renanrramos.easyshopping.model.builder.StockItemBuilder;
import br.com.renanrramos.easyshopping.model.builder.StoreBuilder;
import br.com.renanrramos.easyshopping.model.form.CreditCardForm;
import br.com.renanrramos.easyshopping.model.form.OrderItemForm;
import br.com.renanrramos.easyshopping.model.form.StockItemForm;
import org.instancio.Instancio;

import static org.instancio.Select.field;

/**
 * @author renan.ramos
 *
 */
public class EasyShoppingUtil {

	public static Address getAddressInstance() {
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

	
	public static Customer getCustomerInstance() {
		Customer customer = new Customer();
		customer.setCpf("12345684522");
		customer.setEmail("email@mail.com");
		customer.setId(1L);
		customer.setName("name");
		customer.setProfile(Profile.CUSTOMER);
		return customer;
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
		final CreditCard creditCard = new CreditCard();
		creditCard.setId(id);
		creditCard.setCode(123);
		creditCard.setCreditCardNumber("1234 1234 1234 1234");
		creditCard.setOwnerName("OWNER NAME");
		return creditCard;
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
		return new OrderItemForm(1L, 1L, 2, 3.0, 6.0, "productName");
	}

	public static Order getOrderInstance() {
		return OrderBuilder.builder().withCustomerId("customerId").withOrderNumber("orderNumber").build();
	}

	public static Product getProductInstance() {
		return ProductBuilder.builder().withCompany("companyId").withDescription("description").withId(1L)
				.withName("productName").withPrice(20.0).build();
	}

	public static Purchase getPurchaseInstance(Long id) {
		return PurchaseBuilder.builder().withId(id).withAddress(getAddressInstance())
				.withCreditCard(getCreditCardInstance(1L))
				.withCustomerId("customerId").withOrder(getOrderInstance()).build();
	}

	public static Store getStoreInstance(Long id) {
		return StoreBuilder.builder().withId(id).withCorporateName("corporateName").withName("name")
				.withRegisteredNumber("registeredNumber").build();
	}

	public static Stock getStockInstance(Long id) {
		return StockBuilder.builder().withId(id).withName("stockName").build();
	}

	public static StockItemForm getStockItemForm(Long productId, Long stockId) {
		return new StockItemForm(productId, 20.0, 5.0, 10, stockId, "productName");
	}

	public static StockItem getStockItemInstance() {
		return StockItemBuilder.builder().withId(1L).withCurrentAmount(10).withMaxAmount(20.0).withMinAmount(5.0)
				.withProductName("productName").build();
	}

	public static User getUserInstance() {
		User user = new User();
		user.setEmail("user@mail.com");
		user.setId(1L);
		user.setName("user");
		user.setProfile(Profile.ADMINISTRATOR);
		user.setSync(true);
		user.setTokenId("tokenId");
		return user;
	}
}
