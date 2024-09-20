package br.com.renanrramos.easyshopping.interfaceadapter.gateway;

import br.com.renanrramos.easyshopping.constants.messages.ExceptionMessagesConstants;
import br.com.renanrramos.easyshopping.core.domain.CreditCard;
import br.com.renanrramos.easyshopping.infra.controller.entity.page.PageResponse;
import br.com.renanrramos.easyshopping.interfaceadapter.gateway.factory.PageableFactory;
import br.com.renanrramos.easyshopping.interfaceadapter.mapper.CreditCardMapper;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.CreditCardRepositoy;
import br.com.renanrramos.easyshopping.interfaceadapter.repository.constants.EasyShoppingSqlConstants;
import br.com.renanrramos.easyshopping.model.CreditCardEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardGatewayImplTest {

    @Mock
    private CreditCardRepositoy creditCardRepository;

    @InjectMocks
    private CreditCardGatewayImpl creditCardGateway;

    @Test
    void findCreditCardByCustomerId_withCustomerId_shouldReturnCreditCardPageResponse() {
        // Arrange
        final Integer pageNumber = 1;
        final Integer pageSize = 1;
        final String sortBy = "asc";
        final String customerId = "customerId";
        final List<CreditCardEntity> creditCardEntities = Instancio.createList(CreditCardEntity.class);
        final Pageable page = new PageableFactory()
                .withPageNumber(pageNumber)
                .withPageSize(pageSize)
                .withSortBy(sortBy)
                .buildPageable();
        final PageImpl<CreditCardEntity> expectedResponse = new PageImpl<>(
                creditCardEntities, page, creditCardEntities.size());
        when(creditCardRepository.findCreditCardByCustomerId(page, customerId)).thenReturn(expectedResponse);

        // Act
        final PageResponse<CreditCard> pageResponse =
                creditCardGateway.findCreditCardByCustomerId(pageNumber, pageSize, sortBy, customerId);

        // Assert
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getTotalElements()).isEqualTo(expectedResponse.getTotalElements());
        assertThat(pageResponse.getTotalPages()).isEqualTo(expectedResponse.getTotalPages());
    }

    @Test
    void saveCreditCard_withCreditCard_shouldRunSuccessfully() {
        // Arrange
        final CreditCard creditCard = Instancio.of(CreditCard.class).create();
        final CreditCardEntity creditCardEntity =
                CreditCardMapper.INSTANCE.mapCreditCardToCreditCardEntity(creditCard);
        when(creditCardRepository.save(creditCardEntity)).thenReturn(creditCardEntity);
        // Act
        final CreditCard creditCardResponse = creditCardGateway.saveCreditCard(creditCard);
        // Assert
        assertThat(creditCardResponse).isNotNull();
        assertThat(creditCardResponse).usingRecursiveComparison().isEqualTo(creditCard);
        verify(creditCardRepository).save(creditCardEntity);
    }

    @Test
    void findCreditCardById_whenCreditCardIdExists_shouldReturnCreditCard() {
        // Arrange
        final Long creditCardId = 1L;
        final Optional<CreditCardEntity> expectedCreditCardEntity = Optional.of(Instancio.create(CreditCardEntity.class));
        final CreditCard expectedCreditCardResponse =
                CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(expectedCreditCardEntity.get());
        when(creditCardRepository.findById(creditCardId)).thenReturn(expectedCreditCardEntity);
        // Act
        final CreditCard creditCard = creditCardGateway.findCreditCardById(creditCardId);
        // Assert
        assertThat(creditCard).isNotNull();
        assertThat(creditCard).usingRecursiveComparison().isEqualTo(expectedCreditCardResponse);
        verify(creditCardRepository).findById(creditCardId);
    }

    @Test
    void findCreditCardById_whenCreditCardIdNotFound_shouldThrowException() {
        // Arrange
        final Long creditCardId = 1L;
        when(creditCardRepository.findById(creditCardId))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> creditCardGateway.findCreditCardById(creditCardId));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
        verify(creditCardRepository).findById(creditCardId);
    }

    @Test
    void updateCreditCard_whenCreditCardIdExists_shouldUpdateSuccessfully() {
        // Arrange
        final Long creditCardId = 1L;
        final CreditCardEntity creditCardEntity = Instancio.of(CreditCardEntity.class)
                .set(field("id"), creditCardId)
                .create();
        final CreditCard creditCardRequest = CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardEntity);
        final CreditCard expectedResponse = CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardEntity);
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.of(creditCardEntity));
        when(creditCardRepository.save(creditCardEntity)).thenReturn(creditCardEntity);
        // Act
        final CreditCard creditCard = creditCardGateway.updateCreditCard(creditCardRequest);
        // Assert
        assertThat(creditCard).isNotNull();
        assertThat(creditCard).usingRecursiveComparison().isEqualTo(expectedResponse);
        verify(creditCardRepository).findById(creditCardId);
        verify(creditCardRepository).save(creditCardEntity);
    }

    @Test
    void updateCreditCard_whenCreditCardIdNotFound_shouldThrowException() {
        // Arrange
        final Long creditCardId = 1L;
        final CreditCardEntity creditCardEntity = Instancio.of(CreditCardEntity.class)
                .set(field("id"), creditCardId)
                .create();
        final CreditCard creditCardRequest = CreditCardMapper.INSTANCE.mapCreditCardEntityToCreditCard(creditCardEntity);
        when(creditCardRepository.findById(creditCardId))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> creditCardGateway.updateCreditCard(creditCardRequest));
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
        verify(creditCardRepository).findById(creditCardId);
        verify(creditCardRepository, never()).save(creditCardEntity);
    }

    @Test
    void remove_whenCreditCardIdExists_shouldUpdateSuccessfully() {
        // Arrange
        final Long creditCardId = 1L;
        final CreditCardEntity creditCardEntity = Instancio.of(CreditCardEntity.class)
                .set(field("id"), creditCardId)
                .create();
        when(creditCardRepository.findById(creditCardId)).thenReturn(Optional.of(creditCardEntity));
        // Act
        creditCardGateway.remove(creditCardId);
        // Assert
        verify(creditCardRepository).findById(creditCardId);
        verify(creditCardRepository).removeById(creditCardId);
    }

    @Test
    void remove_whenCreditCardIdNotFound_shouldThrowException() {
        // Arrange
        final Long creditCardId = 1L;
        when(creditCardRepository.findById(creditCardId))
                .thenThrow(new EntityNotFoundException(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND));
        // Act
        final EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> creditCardGateway.remove(creditCardId));
        // Assert
        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessagesConstants.CREDIT_CARD_NOT_FOUND);
        verify(creditCardRepository).findById(creditCardId);
        verify(creditCardRepository, never()).removeById(creditCardId);
    }
}