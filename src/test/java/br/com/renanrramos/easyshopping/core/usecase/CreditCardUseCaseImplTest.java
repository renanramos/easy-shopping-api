package br.com.renanrramos.easyshopping.core.usecase;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.core.gateway.CreditCardGateway;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardUseCaseImplTest {

    @Mock
    private CreditCardGateway creditCardGateway;
    @InjectMocks
    private CreditCardUseCaseImpl creditCardUseCase;

    @Test
    void findCreditCardByCustomerId_whenCustomerIdIsValid_shouldRunSuccessfully() {
        // Arrange
        final String customerId = "customerId";
        final String sortBy = "asc";
        final Integer pageSize = 1;
        final Integer pageNumber = 1;
        final List<CreditCard> creditCards = Instancio.ofList(CreditCard.class)
                .size(3)
                .withMaxDepth(2)
                .create();
        final List<CreditCardDTO> expectedCreditCardDTOList =
                CreditCardMapper.INSTANCE.mapCreditCardListToCreditCardDTOList(creditCards);
        final PageResponse<CreditCard> pageResponse = new PageResponse<>(3L, 1, creditCards);
        when(creditCardGateway.findCreditCardByCustomerId(pageNumber, pageSize, sortBy, customerId))
                .thenReturn(pageResponse);
        // Act
        final PageResponse<CreditCardDTO> creditCardPagedResponse =
                creditCardUseCase.findCreditCardByCustomerId(pageNumber, pageSize, sortBy, customerId);
        // Assert
        assertThat(creditCardPagedResponse).isNotNull();
        assertThat(creditCardPagedResponse.getResponseItems())
                .hasSize(expectedCreditCardDTOList.size())
                .hasSameElementsAs(expectedCreditCardDTOList);
    }

    @Test
    void saveCreditCard_withCreditCard_shouldRunSuccessfully() {
        // Arrange
        final CreditCard creditCard = Instancio.of(CreditCard.class).withMaxDepth(2).create();
        final CreditCardDTO expectedResponse = CreditCardMapper.INSTANCE.mapCreditCardToCreditCardDTO(creditCard);
        when(creditCardGateway.saveCreditCard(creditCard)).thenReturn(creditCard);
        // Act
        final CreditCardDTO creditCardDTO = creditCardUseCase.saveCreditCard(creditCard);
        // Assert
        assertThat(creditCardDTO).isNotNull();
        assertThat(creditCardDTO)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void findCreditCardById_withCreditCardId_shouldRunSuccessfully() {
        // Arrange
        final Long creditCardId = 1L;
        final CreditCard creditCard = Instancio.of(CreditCard.class).withMaxDepth(2).create();
        final CreditCardDTO expectedResponse = CreditCardMapper.INSTANCE.mapCreditCardToCreditCardDTO(creditCard);
        when(creditCardGateway.findCreditCardById(creditCardId)).thenReturn(creditCard);
        // Act
        final CreditCardDTO creditCardResponse = creditCardUseCase.findCreditCardById(creditCardId);
        // Assert
        assertThat(creditCardResponse).isNotNull();
        assertThat(creditCardResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void updateCreditCard_withCreditCard_shouldRunSuccessfully() {
        // Arrange
        final CreditCard creditCard = Instancio.of(CreditCard.class).withMaxDepth(2).create();
        final CreditCardDTO expectedResponse = CreditCardMapper.INSTANCE.mapCreditCardToCreditCardDTO(creditCard);
        when(creditCardGateway.updateCreditCard(creditCard)).thenReturn(creditCard);
        // Act
        final CreditCardDTO creditCardResponse = creditCardUseCase.updateCreditCard(creditCard);
        // Assert
        assertThat(creditCardResponse).isNotNull();
        assertThat(creditCardResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @Test
    void remove_withCreditCardId_shouldRunSuccessfully() {
        // Arrange
        final Long creditCardId = 1L;
        // Act / Assert
        creditCardUseCase.remove(creditCardId);
    }
}