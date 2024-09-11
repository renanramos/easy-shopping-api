package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.model.form.CustomerForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    void mapCustomerToCustomerDTO_withCustomer_shouldMapToCustomerDTO() {
        final Customer customer = Instancio.create(Customer.class);

        final CustomerDTO customerDTO = CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customer);

        assertCustomerDTO(customerDTO, customer);
    }

    @Test
    void mapCustomerListToCustomerDTOList_withCustomerList_shouldMapToCustomerDTOList() {
        final List<Customer> customerList = Instancio.ofList(Customer.class)
                .size(10)
                .create();

        final List<CustomerDTO> customerDTOS = CustomerMapper.INSTANCE.mapCustomerListToCustomerDTOList(customerList);

        assertCustomerDTOList(customerDTOS, customerList);
    }

    @Test
    void mapCustomerFormToCustomer_withCustomerForm_shouldMapToCustomer() {
        final CustomerForm customerForm = Instancio.of(CustomerForm.class)
                .create();

        final Customer customer = CustomerMapper.INSTANCE.mapCustomerFormToCustomer(customerForm);

        assertCustomer(customer, customerForm);
    }

    @Test
    void mapCustomerFormToUpdateCustomer_whenCustomerFormUpdateOperation_shouldMapToCustomerOnlyDifferentFields() {
        // Arrange
        final CustomerForm customerForm = Instancio.of(CustomerForm.class)
                .create();
        final Customer customer = CustomerMapper.INSTANCE.mapCustomerFormToCustomer(customerForm);
        final String originalName = customer.getName();
        customerForm.setName(null);
        customerForm.setCpf("123.456.789-10");
        customerForm.setEmail("customer@mail.com");
        // Act
        CustomerMapper.INSTANCE.mapCustomerFormToUpdateCustomer(customer, customerForm);
        // Assert
        assertThat(customer).isNotNull();
        assertThat(customer.getEmail()).isEqualTo(customerForm.getEmail());
        assertThat(customer.getCpf()).isEqualTo(customerForm.getCpf());
        assertThat(customer.getName()).isNotNull().isEqualTo(originalName);
    }

    private void assertCustomer(final Customer customer, final CustomerForm customerForm) {
        assertThat(customer).isNotNull();
        assertThat(customer.getCpf()).isEqualTo(customerForm.getCpf());
        assertThat(customer.getProfile()).isEqualTo(customerForm.getProfile());
        assertThat(customer.getName()).isEqualTo(customerForm.getName());
        assertThat(customer.getEmail()).isEqualTo(customerForm.getEmail());
    }

    private void assertCustomerDTOList(final List<CustomerDTO> customerDTOS, final List<Customer> customerList) {
        assertThat(customerDTOS).hasSize(customerList.size());
        int index = 0;
        for(final CustomerDTO customerDTO : customerDTOS) {
           assertCustomerDTO(customerDTO, customerList.get(index));
           index++;
        }
    }

    private static void assertCustomerDTO(final CustomerDTO customerDTO, final Customer customer) {
        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());
        assertThat(customerDTO.getProfile().name()).isEqualTo(customer.getProfile().name());
        assertThat(customerDTO.getName()).isEqualTo(customer.getName());
        assertThat(customerDTO.getCpf()).isEqualTo(customer.getCpf());
        assertThat(customerDTO.getEmail()).isEqualTo(customer.getEmail());
        assertThat(customerDTO.getTokenId()).isEqualTo(customer.getTokenId());
    }
}