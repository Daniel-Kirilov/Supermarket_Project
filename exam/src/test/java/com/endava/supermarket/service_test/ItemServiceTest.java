package com.endava.supermarket.service_test;

import com.endava.supermarket.dto.item_dtos.CreateItemDto;
import com.endava.supermarket.model.Item;
import com.endava.supermarket.model.enums.ItemType;
import com.endava.supermarket.repository.ItemRepository;
import com.endava.supermarket.service.Impl.ItemServiceImpl;
import com.endava.supermarket.service.ItemService;
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

@RunWith(SpringRunner.class)
public class ItemServiceTest {

    public static final String NAME = "bread";

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).then(AdditionalAnswers.returnsFirstArg());
        Mockito.when(itemRepository.existsByName(NAME)).thenReturn(true);

    }

    private Item getItem(String id, String name, String itemType) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setItemType(ItemType.valueOf(itemType));

        return item;
    }

    private CreateItemDto getItemDto(String name, String itemType) {
        CreateItemDto item = new CreateItemDto();
        item.setName(name);
        item.setItemType(itemType);

        return item;
    }

    @Test
    public void whenValidSaveItem_shouldReturnItem() {
        CreateItemDto itemToBeSaved = getItemDto("eggs", "FOOD");
        Item item = itemService.createItem(itemToBeSaved);

        Assertions.assertEquals(itemToBeSaved.getName(), item.getName());
        Assertions.assertEquals(itemToBeSaved.getItemType(), item.getItemType().toString());

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidItemTypeSaveItem_shouldReturnIllegalArgumentException() {
        CreateItemDto itemToBeSaved = getItemDto("milk", "INVALID");
        itemService.createItem(itemToBeSaved);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenExistingNameSaveItem_shouldReturnIllegalArgumentException() {
        CreateItemDto itemToBeSaved = getItemDto(NAME, "FOOD");
        itemService.createItem(itemToBeSaved);
    }

    @TestConfiguration
    static class ItemServiceTestConfiguration {
        @Bean
        public ItemService itemService(ItemRepository itemRepository) {
            return new ItemServiceImpl(itemRepository);
        }
    }
}
