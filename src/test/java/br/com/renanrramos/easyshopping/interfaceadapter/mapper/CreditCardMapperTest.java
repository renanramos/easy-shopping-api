package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.dto.CreditCardDTO;
import br.com.renanrramos.easyshopping.model.form.CreditCardForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

class CreditCardMapperTest {

    @Test
    void mapCreditCardToCreditCardDTO_withCreditCard_shouldMapToCreditCardDTO() {
        final CreditCard creditCard = Instancio.create(CreditCard.class);

        final CreditCardDTO creditCardDTO = CreditCardMapper.INSTANCE.mapCreditCardToCreditCardDTO(creditCard);

        assertCreditCardDTO(creditCardDTO, creditCard);
    }

    @Test
    void mapCreditCardListToCreditCardDTOList_withCreditCardList_shouldMapToCreditCardDTOList() {
        final List<CreditCard> creditCards = Instancio.ofList(CreditCard.class).size(10).create();

        final List<CreditCardDTO> creditCardDTOS = CreditCardMapper.INSTANCE
                .mapCreditCardListToCreditCardDTOList(creditCards);

        assertCreditCardDTOList(creditCardDTOS, creditCards);

    }

    @Test
    void mapCreditCardFormToCreditCard_withCreditCardForm_shouldMapToCreditCard() {
        final CreditCardForm creditCardForm = Instancio.of(CreditCardForm.class)
                .supply(field(CreditCardForm::getValidDate), () -> LocalDate.now().toString())
                .create();

        final CreditCard creditCard = CreditCardMapper.INSTANCE
                .mapCreditCardFormToCreditCard(creditCardForm);

        assertCreditCard(creditCard, creditCardForm);
    }

    @Test
    void mapCreditCardFormToUpdateCreditCard_whenCreditCardUpdateOperation_shouldMapOnlyDifferentValues() {
        // Arrange
        final CreditCardForm creditCardForm = Instancio.of(CreditCardForm.class)
                .supply(field(CreditCardForm::getValidDate), () -> LocalDate.now().toString())
                .create();
        final CreditCard creditCard = CreditCardMapper.INSTANCE
                .mapCreditCardFormToCreditCard(creditCardForm);
        creditCardForm.setCode(123);
        creditCardForm.setOwnerName("Owner Name");
        // Act
        CreditCardMapper.INSTANCE.mapCreditCardFormToUpdateCreditCard(creditCard, creditCardForm);

        assertCreditCard(creditCard, creditCardForm);
    }

    private void assertCreditCard(final CreditCard creditCard, final CreditCardForm creditCardForm) {
        assertThat(creditCard).isNotNull();
        assertThat(creditCard.getCreditCardNumber()).isEqualTo(creditCardForm.getCreditCardNumber());
        assertThat(creditCard.getCode()).isEqualTo(creditCardForm.getCode());
        assertThat(creditCard.getValidDate()).isEqualTo(creditCardForm.getValidDate());
        assertThat(creditCard.getOwnerName()).isEqualTo(creditCardForm.getOwnerName());
    }

    private void assertCreditCardDTOList(final List<CreditCardDTO> creditCardDTOS, final List<CreditCard> creditCards) {
        assertThat(creditCardDTOS).hasSize(creditCards.size());
        int index = 0;
        for(final CreditCardDTO creditCardDTO : creditCardDTOS) {
            assertCreditCardDTO(creditCardDTO, creditCards.get(index));
            index++;
        }
    }

    private static void assertCreditCardDTO(final CreditCardDTO creditCardDTO, final CreditCard creditCard) {
        assertThat(creditCardDTO).isNotNull();
        assertThat(creditCardDTO.getCreditCardNumber()).isEqualTo(creditCard.getCreditCardNumber());
        assertThat(creditCardDTO.getId()).isEqualTo(creditCard.getId());
        assertThat(creditCardDTO.getCode()).isEqualTo(creditCard.getCode());
        assertThat(creditCardDTO.getValidDate()).isEqualTo(creditCard.getValidDate());
        assertThat(creditCardDTO.getOwnerName()).isEqualTo(creditCard.getOwnerName());
    }
}