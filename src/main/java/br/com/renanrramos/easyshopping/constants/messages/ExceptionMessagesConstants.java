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
	public static final String SUBCATEGORY_NOT_FOUND = "Subcategoria não localizada.";
	public static final String USER_NOT_FOUND = "Usuário não localizado.";
	public static final String ADMINISTRATOR_NOT_FOUND = "Administrador não localizado.";
	public static final String STOCK_NOT_FOUND = "o estoque informado não foi lcocalizado.";
	public static final String STOCK_ITEM_NOT_FOUND = "Item do estoque informado não foi lcocalizado.";
	public static final String ORDER_NOT_FOUND = "Pedido não localizado";

	public static final String INVALID_ID = "O id informado é inválido.";
	public static final String CUSTOMER_ID_NOT_FOUND_ON_REQUEST = "Id do cliente é obrigatório.";
	public static final String COMPANY_ID_NOT_FOUND_ON_REQUEST = "Id da empresa é obrigatório.";
	public static final String PRODUCT_CATEGORY_ID_NOT_FOUND_ON_REQUEST = "Id da categoria do produto é obrigatório.";
	public static final String PRODUCT_ID_NOT_FOUND_ON_REQUEST = "Id do produto é obrigatório.";
	public static final String STORE_ID_NOT_FOUND_ON_REQUEST = "Id da loja é obrigatório.";
	public static final String STOCK_ID_NOT_FOUND_ON_REQUEST = "Id do estoque é obrigatório.";
	public static final String STOCK_ITEM_ID_NOT_FOUND_ON_REQUEST = "Id do item do estoque é obrigatório.";
	public static final String ORDER_ID_NOT_FOUND_ON_REQUEST = "Id do pedido é obrigatório";
	public static final String ADDRESS_ID_NOT_FOUND_ON_REQUEST = "Id do endereço é obrigatório";
	public static final String CREDIT_CARD_ID_NOT_FOUND_ON_REQUEST = "Id do cartão de crédito é obrigatório";

	public static final String CPF_ALREADY_EXIST = "O CPF informado já está cadastrado.";
	public static final String CNPJ_ALREADY_EXIST = "O CNPJ informado já está cadastrado.";

	public static final String INVALID_FIELDS_TITLE = "Valor do campo inválido!";

	public static final String NOT_FOUND_TITLE = "Não encontrado";
	public static final String ERRORS_FOUND = "Erros encontrados";

	public static final String INVALID_CREDENTIALS = "Email/senha inválidos.";

	public static final String CANNOT_REMOVE_PRODUCT_CATEGORY_IN_USE = "A categoria do produto ainda é utilizada e não pode ser removida.";
	public static final String INTERNAL_ERROR = "Erro no momento de efetuar a ação";

	public static final String WRONG_PRODUCT_ID = "Produto informado não é igual ao que está no parâmetro.";
	public static final String INVALID_FILE = "Arquivo inválido ou nulo.";
	public static final String PRODUCT_IMAGE_NOT_FOUND = "Imagem não localizada";
	public static final String PRODUCT_ALREADY_IN_STOCK = "O produto informado já está incluso no estoque.";
	public static final String PRODUCT_IS_NOT_IN_STOCK = "O produto não está alocado em nenhum estoque.";

	private ExceptionMessagesConstants() {
		// Intentionally empty
	}
}
