package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Customer;
import br.com.renanrramos.easyshopping.model.dto.CustomerDTO;
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