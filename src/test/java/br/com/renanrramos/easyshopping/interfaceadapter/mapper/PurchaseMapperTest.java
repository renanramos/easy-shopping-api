package br.com.renanrramos.easyshopping.interfaceadapter.mapper;


import br.com.renanrramos.easyshopping.interfaceadapter.entity.Purchase;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.PurchaseDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PurchaseMapperTest {

    @Test
    void mapPurchaseToPurchaseDTO_withPurchase_shouldMapToPurchaseDTO() {
        final Purchase purchase = Instancio.of(Purchase.class).create();

        final PurchaseDTO purchaseDTO = PurchaseMapper.INSTANCE.mapPurchaseToPurchaseDTO(purchase);

        assertPurchaseDTO(purchaseDTO, purchase);
    }

    @Test
    void mapPurchaseListToPurchaseDTOList_withPurchaseList_shouldMapToPurchaseDTOList() {
        final List<Purchase> purchaseList = Instancio.ofList(Purchase.class).create();

        final List<PurchaseDTO> purchaseDTOList = PurchaseMapper.INSTANCE.mapPurchaseListToPurchaseDTOList(purchaseList);

        assertPurchaseDTOList(purchaseDTOList, purchaseList);
    }

    private void assertPurchaseDTOList(final List<PurchaseDTO> purchaseDTOList, final List<Purchase> purchaseList) {
        assertThat(purchaseDTOList).hasSize(purchaseList.size());
        int index = 0;
        for (final PurchaseDTO purchaseDTO : purchaseDTOList) {
            assertPurchaseDTO(purchaseDTO, purchaseList.get(index));
            index++;
        }
    }

    private void assertPurchaseDTO(final PurchaseDTO purchaseDTO, final Purchase purchase) {
        assertThat(purchaseDTO).isNotNull();
        assertThat(purchaseDTO.getId()).isEqualTo(purchase.getId());
        assertThat(purchaseDTO.getAddressId()).isEqualTo(purchase.getAddress().getId());
        assertThat(purchaseDTO.getCustomerId()).isEqualTo(purchase.getCustomerId());
        assertThat(purchaseDTO.getOrderId()).isEqualTo(purchase.getOrder().getId());
        assertThat(purchaseDTO.getDate()).isEqualTo(purchase.getPurchaseDate());
        assertThat(purchaseDTO.getCreditCardId()).isEqualTo(purchase.getCreditCard().getId());
    }
}