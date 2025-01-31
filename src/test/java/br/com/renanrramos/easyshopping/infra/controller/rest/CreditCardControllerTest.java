package br.com.renanrramos.easyshopping.infra.controller.rest;

import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.CreditCardForm;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.ParametersRequest;
import br.com.renanrramos.easyshopping.infra.delegate.CreditCardDelegate;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import br.com.renanrramos.easyshopping.service.AuthenticationService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditCardControllerTest {

    @Mock
    private CreditCardDelegate creditCardDelegate;
    @Mock
    private Authentication authentication;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private UriComponentsBuilder uriComponentsBuilder;
    @Mock
    private UriComponents uriComponents;

    private URI uri;
    @InjectMocks
    private CreditCardController creditCardController;

    @Test
    void saveCreditCard() {
        // Arrange
        final CreditCardForm creditCardForm = Instancio.of(CreditCardForm.class)
                .set(field("validDate"), "2024-01-01")
                .create();
        when(creditCardDelegate.saveCreditCard(creditCardForm)).thenReturn(buildCreditCardDTO(creditCardForm));
        // Act
        final ResponseEntity<CreditCardDTO> responseEntity =
                creditCardController.saveCreditCard(creditCardForm);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        verify(creditCardDelegate).saveCreditCard(creditCardForm);
    }

    @Test
    void getCreditCards() {
        // Arrange
        final String customerId = "customerId";
        final List<CreditCard> creditCards = Instancio.ofList(CreditCard.class)
                .size(3)
                .create();
        final List<CreditCardDTO> creditCardDTOS =
                CreditCardMapper.INSTANCE.mapCreditCardListToCreditCardDTOList(creditCards);
        final PageResponse<CreditCardDTO> expectedPageResponse =
                new PageResponse<>(3L, 1, creditCardDTOS);
        when(authenticationService.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(customerId);
        when(creditCardDelegate.findCreditCardByCustomerId(new ParametersRequest(), customerId))
                .thenReturn(expectedPageResponse);
        // Act
        final ResponseEntity<PageResponse<CreditCardDTO>> creditCardsResponse =
                creditCardController.getCreditCards(ParametersRequest.DEFAULT_PAGE_NUMBER,
                        ParametersRequest.DEFAULT_PAGE_SIZE,
                        ParametersRequest.DEFAULT_SORT_BY);
        // Assert
        assertThat(creditCardsResponse).isNotNull();
        assertThat(creditCardsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        final PageResponse<CreditCardDTO> pageResponse = creditCardsResponse.getBody();
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalPages()).isEqualTo(expectedPageResponse.getTotalPages());
        assertThat(pageResponse.getTotalElements()).isEqualTo(expectedPageResponse.getTotalElements());
        assertThat(pageResponse.getResponseItems()).hasSameElementsAs(expectedPageResponse.getResponseItems());
        verify(creditCardDelegate).findCreditCardByCustomerId(new ParametersRequest(), customerId);
    }

    @Test
    void getCreditCardById() {
        // Arrange
        final Long creditCardId = 1L;
        final CreditCard creditCard = Instancio.of(CreditCard.class)
                .set(field("id"), creditCardId)
                .create();
        final CreditCardDTO creditCardDTO = buildCreditCardDTO(creditCard);
        when(creditCardDelegate.findCreditCardById(creditCardId)).thenReturn(creditCardDTO);
        // Act
        final ResponseEntity<CreditCardDTO> responseEntity =
                creditCardController.getCreditCardById(creditCardId);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(creditCardDTO);
    }

    @Test
    void updateCreditCard() {
        // Arrange
        final Long creditCardId = 1L;
        final CreditCardForm creditCardForm = Instancio.of(CreditCardForm.class)
                .set(field("validDate"), "2024-01-01")
                .create();
        final CreditCardDTO expectedResponse = buildCreditCardDTO(creditCardForm);
        when(creditCardDelegate.updateCreditCard(creditCardForm, creditCardId)).thenReturn(expectedResponse);
        when(uriComponentsBuilder.path(anyString())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.buildAndExpand(eq(expectedResponse.getId()))).thenReturn(uriComponents);
        when(uriComponents.encode()).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(uri);

        // Act
        final ResponseEntity<CreditCardDTO> responseEntity =
                creditCardController.updateCreditCard(creditCardId, creditCardForm, uriComponentsBuilder);
        // Assert
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    private static CreditCardDTO buildCreditCardDTO(final CreditCardForm creditCardForm) {
        final CreditCard creditCard =
                CreditCardMapper.INSTANCE.mapCreditCardFormToCreditCard(creditCardForm);
        return buildCreditCardDTO(creditCard);
    }

    private static CreditCardDTO buildCreditCardDTO(final CreditCard creditCard) {
        return CreditCardMapper.INSTANCE.mapCreditCardToCreditCardDTO(creditCard);
    }
}