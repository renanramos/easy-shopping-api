package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.CreditCard;
import br.com.renanrramos.easyshopping.model.dto.CreditCardDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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