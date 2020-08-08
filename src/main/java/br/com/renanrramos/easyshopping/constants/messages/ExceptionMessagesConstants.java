/**------------------------------------------------------------
 * Project: easy-shopping
 * 
 * Creator: renan.ramos - 16/07/2020
 * ------------------------------------------------------------
 */
package br.com.renanrramos.easyshopping.constants.messages;

/**
 * @author renan.ramos
 *
 */
public class ExceptionMessagesConstants {

	public static final String CUSTOMER_NOT_FOUND = "Cliente não localizado.";
	public static final String COMPANY_NOT_FOUND = "Empresa não localizada.";
	public static final String ADDRESS_NOT_FOUND = "Endereço não localizado.";
	public static final String ACCOUNT_NOT_FOUND = "A conta informada não foi localizada.";
	public static final String PRODUCT_CATEGORY_NOT_FOUND = "A categoria do produto informada não foi localizada.";
	public static final String PRODUCT_NOT_FOUND = "O produto informado não foi localizado.";
	public static final String STORE_NOT_FOUND = "A loja informada não foi lcocalizada.";
	public static final String CREDIT_CARD_NOT_FOUND = "Cartão de crédito não localizado.";
	
	public static final String INVALID_ID = "O id informado é inválido.";
	public static final String CUSTOMER_ID_NOT_FOUND_ON_REQUEST = "Id do cliente é obrigatório.";
	public static final String COMPANY_ID_NOT_FOUND_ON_REQUEST = "Id da empresa é obrigatório.";
	public static final String PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST = "Id da categoria do produto é obrigatório.";
	public static final String PRODUCT_ID_NOT_FOUND_ON_REQUEST = "Id do produto é obrigatório.";
	public static final String STORE_ID_NOT_FOUND_ON_REQUEST = "Id da loja é obrigatório.";

	public static final String EMAIL_ALREADY_EXIST = "O email informado já é utilizado.";
	public static final String CPF_ALREADY_EXIST = "O CPF informado já está cadastrado.";
	public static final String CNPJ_ALREADY_EXIST = "O CNPJ informado já está cadastrado.";

	public static final String INVALID_FIELDS_TITLE = "Valor do campo inválido!";

	private ExceptionMessagesConstants() {
		// Intentionally empty
	}
}
