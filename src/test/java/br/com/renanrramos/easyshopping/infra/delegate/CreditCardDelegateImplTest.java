package br.com.renanrramos.easyshopping.infra.delegate;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.core.usecase.CreditCardUseCase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardDelegateImplTest {

    @Mock
    private CreditCardUseCase creditCardUseCase;

    @InjectMocks
    private CreditCardDelegateImpl creditCardDelegate;

    @Test
    void findCreditCardByCustomerId_whenCustomerIdIsValid_shouldRunSuccessfully() {
        // Arrange
        final String customerId = "customerId";
        final ParametersRequest parametersRequest = new ParametersRequest();
        final List<CreditCard> creditCards = Instancio.ofList(CreditCard.class)
                .size(3)
                .withMaxDepth(2)
                .create();
        final List<CreditCardDTO> expectedCreditCardDTOList =
                CreditCardMapper.INSTANCE.mapCreditCardListToCreditCardDTOList(creditCards);
        final PageResponse<CreditCardDTO> pageResponse =
                new PageResponse<>(3L, 1, expectedCreditCardDTOList);
        when(creditCardUseCase.findCreditCardByCustomerId(parametersRequest, customerId))
                .thenReturn(pageResponse);
        // Act
        final PageResponse<CreditCardDTO> creditCardPagedResponse =
                creditCardDelegate.findCreditCardByCustomerId(parametersRequest, customerId);
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
        when(creditCardUseCase.saveCreditCard(creditCard)).thenReturn(expectedResponse);
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
        final CreditCardDTO expectedResponse = Instancio.of(CreditCardDTO.class).withMaxDepth(2).create();
        when(creditCardUseCase.findCreditCardById(creditCardId)).thenReturn(expectedResponse);
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
        final CreditCardDTO expectedResponse = Instancio.of(CreditCardDTO.class).withMaxDepth(2).create();
        when(creditCardUseCase.updateCreditCard(creditCard)).thenReturn(expectedResponse);
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
        creditCardDelegate.remove(creditCardId);
    }
}