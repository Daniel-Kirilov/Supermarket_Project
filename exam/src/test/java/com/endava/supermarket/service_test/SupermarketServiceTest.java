package com.endava.supermarket.service_test;

import com.endava.supermarket.dto.supermarket_dtos.CreateSupermarketDto;
import com.endava.supermarket.dto.supermarket_dtos.GetSupermarketByNameAndAddressDto;
import com.endava.supermarket.model.Item;
import com.endava.supermarket.model.Supermarket;
import com.endava.supermarket.model.enums.ItemType;
import com.endava.supermarket.repository.ItemRepository;
import com.endava.supermarket.repository.SuperMarketRepository;
import com.endava.supermarket.repository.SupermarketItemAssociationsRepository;
import com.endava.supermarket.service.Impl.SuperMarketServiceImpl;
import com.endava.supermarket.service.SuperMarketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class SupermarketServiceTest {

    public static final String SUPERMARKET_ID = "8a8081e679cdbb820179cdbbb4020001";
    public static final String ADDRESS = "NATA STOQNOVA 9";
    public static final String WRONG_ADDRESS = "NATA STOQNOVA 900000";
    public static final String WRONG_ID = "87643";
    public static final String ITEM_NAME = "Eggs";
    public static final String SUPERMARKET_NAME = "BILLA-PERNIK";
    public static final ItemType ITEM_TYPE = ItemType.FOOD;
    public static final String ITEM_ID = "8a8081e679cdbb820179cdbbb4020008";

    @Autowired
    private SuperMarketService supermarketService;

    @MockBean
    private SuperMarketRepository superMarketRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private SupermarketItemAssociationsRepository associationsRepository;

    @Before
    public void setUp() {
        Supermarket supermarket = getSupermarket();

        Item item = new Item();
        item.setId(ITEM_ID);
        item.setName(ITEM_NAME);
        item.setItemType(ITEM_TYPE);

        Mockito.when(superMarketRepository.save(Mockito.any(Supermarket.class))).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(itemRepository.findById(ITEM_ID)).thenReturn(Optional.of(item));
        Mockito.when(superMarketRepository.existsByAddress(ADDRESS)).thenReturn(true);
        Mockito.when(superMarketRepository.findById(SUPERMARKET_ID)).thenReturn(Optional.of(supermarket));
        Mockito.when(superMarketRepository.existsById(SUPERMARKET_ID)).thenReturn(true);
        Mockito.when(superMarketRepository.existsById(WRONG_ID)).thenReturn(false);
        Mockito.when(superMarketRepository.getSupermarketByNameAndAddress(SUPERMARKET_NAME, ADDRESS))
                .thenReturn(supermarket);
//        Mockito.when(associationsRepository.saveAll(association))
//                .then(AdditionalAnswers.returnsFirstArg());
    }

    private CreateSupermarketDto getSupermarketDto(String name, String address, String phoneNumber, String workHours) {
        CreateSupermarketDto supermarket = new CreateSupermarketDto();
        supermarket.setName(name);
        supermarket.setAddress(address);
        supermarket.setPhoneNumber(phoneNumber);
        supermarket.setWorkHours(workHours);
        return supermarket;
    }

    private Supermarket getSupermarket() {
        Supermarket supermarket = new Supermarket();
        supermarket.setId(SupermarketServiceTest.SUPERMARKET_ID);
        supermarket.setName(SupermarketServiceTest.SUPERMARKET_NAME);
        supermarket.setAddress(SupermarketServiceTest.ADDRESS);
        supermarket.setPhoneNumber("0889897270");
        supermarket.setWorkHours("08-22:00");
        return supermarket;
    }

    @Test
    public void whenValidSaveSupermarket_shouldReturnSupermarket() {
        CreateSupermarketDto supermarketToBeSaved = getSupermarketDto("Liddle-Lovech", Mockito.anyString(),
                "0877548956", "08:22:30");
        Supermarket supermarket = supermarketService.createSupermarket(supermarketToBeSaved);

        Assertions.assertEquals(supermarketToBeSaved.getName(), supermarket.getName());
        Assertions.assertEquals(supermarketToBeSaved.getAddress(), supermarket.getAddress());
        Assertions.assertEquals(supermarketToBeSaved.getPhoneNumber(), supermarket.getPhoneNumber());
        Assertions.assertEquals(supermarketToBeSaved.getWorkHours(), supermarket.getWorkHours());

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddressExistSupermarket_shouldReturnIllegalArgumentException() {
        CreateSupermarketDto supermarketToBeSaved = getSupermarketDto("Liddle-Lovech", ADDRESS,
                "0877548956", "08:22:30");
        Supermarket supermarket = supermarketService.createSupermarket(supermarketToBeSaved);

    }

    @Test
    public void whenSupermarketIdExists_shouldReturnSupermarket() {
        Assertions.assertEquals(SUPERMARKET_ID, supermarketService.getSuperMarketById(SUPERMARKET_ID).getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSupermarketIdDoesNotExist_shouldReturnIllegalArgumentException() {
        supermarketService.getSuperMarketById(WRONG_ID);
    }

    @Test
    public void whenSupermarketExistByNameAndAddress_shouldReturnSupermarket() {
        GetSupermarketByNameAndAddressDto data = new GetSupermarketByNameAndAddressDto();
        data.setName(SUPERMARKET_NAME);
        data.setAddress(ADDRESS);
        Assertions.assertEquals(SUPERMARKET_ID, supermarketService.getSupermarketByNameAndAddress(data).getId());
        Assertions.assertEquals(SUPERMARKET_NAME, supermarketService.getSupermarketByNameAndAddress(data).getName());
        Assertions.assertEquals(ADDRESS, supermarketService.getSupermarketByNameAndAddress(data).getAddress());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSupermarketNameAndAddressDoesNotExist_shouldReturnIllegalArgumentException() {
        GetSupermarketByNameAndAddressDto data = new GetSupermarketByNameAndAddressDto();
        data.setName(SUPERMARKET_NAME);
        data.setAddress(WRONG_ADDRESS);
        supermarketService.getSupermarketByNameAndAddress(data);
    }

//    @Test
//    @Disabled
//    public void whenAddItemsInSupermarket_shouldReturnSupermarketAndItsItems() {
//        supermarketService.addItemsToSuperMarket(SUPERMARKET_ID, List.of(ITEM_ID));
//        List<SupermarketItemAssociation> association =
//                supermarketService.addItemsToSuperMarket(SUPERMARKET_ID, List.of(ITEM_ID));
//        Assertions.assertEquals(SUPERMARKET_ID, association.get(0).getSupermarketId());
//        //Assertions.assertEquals(ITEM_ID, association.get(0).getItemId());
//
//
//    }

    @TestConfiguration
    static class SupermarketServiceTestConfiguration {
        @Bean
        public SuperMarketService superMarketService(SuperMarketRepository superMarketRepository, ItemRepository itemRepository,
                                                     SupermarketItemAssociationsRepository associationsRepository) {

            return new SuperMarketServiceImpl(superMarketRepository, itemRepository, associationsRepository);
        }
    }
}
