package br.com.renanrramos.easyshopping.interfaceadapter.mapper;

import br.com.renanrramos.easyshopping.model.Store;
import br.com.renanrramos.easyshopping.infra.controller.entity.dto.StoreDTO;
import br.com.renanrramos.easyshopping.infra.controller.entity.form.StoreForm;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StoreMapperTest {

    @Test
    void mapStoreToStoreDTO_withStore_shouldMapToStoreDTO() {
        final Store store = Instancio.of(Store.class)
                .withMaxDepth(3)
                .create();

        final StoreDTO storeDTO = StoreMapper.INSTANCE.mapStoreToStoreDTO(store);

        assertStoreDTO(storeDTO, store);
    }

    @Test
    void mapStoreListToStoreDTOList_withStoreList_shouldMapToStoreDTOList() {
        final List<Store> storeList = Instancio.ofList(Store.class)
                .withMaxDepth(3)
                .create();

        final List<StoreDTO> storeDTOList = StoreMapper.INSTANCE.mapStoreListToStoreDTOList(storeList);

        assertStoreDTOList(storeDTOList, storeList);
    }

    @Test
    void mapStoreFormToStore_withStoreForm_shouldMapStore() {
        final StoreForm storeForm = Instancio.of(StoreForm.class).create();
        final Store store = StoreMapper.INSTANCE.mapStoreFormToStore(storeForm);
        assertStore(store, storeForm);
    }

    @Test
    void mapStoreFormToUpdateStore_whenStoreFormUpdateOperation_shouldMapStoreOnlyDifferentFields() {
        // Arrange
        final StoreForm storeForm = Instancio.of(StoreForm.class).create();
        final Store store = Instancio.of(Store.class)
                .withMaxDepth(2)
                .create();
        final String corporateName = "corporateName";
        store.setCorporateName(corporateName);
        storeForm.setCorporateName(null);
        // Act
        StoreMapper.INSTANCE.mapStoreFormToUpdateStore(store, storeForm);
        // Assert
        assertThat(store).isNotNull();
        assertThat(store.getRegisteredNumber()).isEqualTo(storeForm.getRegisteredNumber());
        assertThat(store.getCorporateName()).isEqualTo(corporateName);
    }

    private void assertStore(final Store store, final StoreForm storeForm) {
        assertThat(store).isNotNull();
        assertThat(store.getCorporateName()).isEqualTo(storeForm.getCorporateName());
        assertThat(store.getRegisteredNumber()).isEqualTo(storeForm.getRegisteredNumber());
    }

    private void assertStoreDTOList(final List<StoreDTO> storeDTOList, final List<Store> storeList) {
        assertThat(storeDTOList).hasSize(storeList.size());
        int index = 0;
        for(final StoreDTO storeDTO : storeDTOList) {
            assertStoreDTO(storeDTO, storeList.get(index));
            index++;
        }
    }

    private void assertStoreDTO(final StoreDTO storeDTO, final Store store) {
        assertThat(storeDTO).isNotNull();
        assertThat(storeDTO.getId()).isEqualTo(store.getId());
        assertThat(storeDTO.getName()).isEqualTo(store.getName());
        assertThat(storeDTO.getCorporateName()).isEqualTo(store.getCorporateName());
        assertThat(storeDTO.getRegisteredNumber()).isEqualTo(store.getRegisteredNumber());
    }
}