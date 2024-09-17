package br.com.renanrramos.easyshopping.infra.config;

import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import br.com.renanrramos.easyshopping.service.impl.AddressService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EasyShoppingConfiguration {

    @NonNull
    private final AddressRepository addressRepository;

    @Bean
    public AddressService addressService() {
        return new AddressService(addressRepository);
    }
}
