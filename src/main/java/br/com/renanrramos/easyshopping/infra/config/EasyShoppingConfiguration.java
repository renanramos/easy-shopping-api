package br.com.renanrramos.easyshopping.infra.config;

import br.com.renanrramos.easyshopping.core.usecase.AddressUseCaseImpl;
import br.com.renanrramos.easyshopping.infra.delegate.AddressDelegateImpl;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.AddressGatewayImpl;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.AddressRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EasyShoppingConfiguration {

    @NotNull
    private final AddressRepository addressRepository;

    @Bean
    public AddressDelegateImpl addressDelegate() {
        return new AddressDelegateImpl(addressUseCase());
    }

    @Bean
    public AddressUseCaseImpl addressUseCase() {
        return new AddressUseCaseImpl(addressGateway());
    }

    @Bean
    public AddressGatewayImpl addressGateway() {
        return new AddressGatewayImpl(addressRepository);
    }
}
