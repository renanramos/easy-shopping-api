package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.core.domain.Customer;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CustomerDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CustomerForm;
import br.com.renanrramos.easyshopping.interfaceadapter.entity.CustomerEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class CustomerEntityMapperTest {

    @Test
    void mapCustomerToCustomerDTO_withCustomer_shouldMapToCustomerDTO() {
        final CustomerEntity customerEntity = Instancio.create(CustomerEntity.class);

        final CustomerDTO customerDTO = CustomerMapper.INSTANCE.mapCustomerToCustomerDTO(customerEntity);

        assertCustomerDTO(customerDTO, customerEntity);
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

    @Test
    void mapCustomerToUpdateCustomerEntity_withValidCustomer_shouldMapFieldsToUpdateCustomerEntity() {
        // Arrange
        final CustomerEntity customerEntity = Instancio.of(CustomerEntity.class)
                .set(field(CustomerEntity::isSync), false).create();
        final Customer customer = Instancio.of(Customer.class).set(field(Customer::isSync), true).create();
        //Act
        CustomerMapper.INSTANCE.mapCustomerToUpdateCustomerEntity(customerEntity, customer);
        //Assert
        assertThat(customerEntity).isNotNull();
        assertThat(customerEntity.isSync()).isEqualTo(customer.isSync());
    }

    private void assertCustomer(final Customer customer, final CustomerForm customerForm) {
        assertThat(customer).isNotNull();
        assertThat(customer.getCpf()).isEqualTo(customerForm.getCpf());
        assertThat(customer.getProfile()).isEqualTo(customerForm.getProfile());
        assertThat(customer.getName()).isEqualTo(customerForm.getName());
        assertThat(customer.getEmail()).isEqualTo(customerForm.getEmail());
    }

    private void assertCustomerDTOList(final List<CustomerDTO> customerDTOS, final List<CustomerEntity> customerEntityList) {
        assertThat(customerDTOS).hasSize(customerEntityList.size());
        int index = 0;
        for (final CustomerDTO customerDTO : customerDTOS) {
            assertCustomerDTO(customerDTO, customerEntityList.get(index));
            index++;
        }
    }

    private static void assertCustomerDTO(final CustomerDTO customerDTO, final CustomerEntity customerEntity) {
        assertThat(customerDTO).isNotNull();
        assertThat(customerDTO.getId()).isEqualTo(customerEntity.getId());
        assertThat(customerDTO.getProfile().name()).isEqualTo(customerEntity.getProfile().name());
        assertThat(customerDTO.getName()).isEqualTo(customerEntity.getName());
        assertThat(customerDTO.getCpf()).isEqualTo(customerEntity.getCpf());
        assertThat(customerDTO.getEmail()).isEqualTo(customerEntity.getEmail());
        assertThat(customerDTO.getTokenId()).isEqualTo(customerEntity.getTokenId());
    }
}