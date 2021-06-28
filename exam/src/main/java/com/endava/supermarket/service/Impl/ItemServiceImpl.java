package com.endava.supermarket.service.Impl;

import com.endava.supermarket.dto.item_dtos.CreateItemDto;
import com.endava.supermarket.model.Item;
import com.endava.supermarket.model.enums.ItemType;
import com.endava.supermarket.repository.ItemRepository;
import com.endava.supermarket.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item createItem(CreateItemDto itemDto) {
        if (!existItemType(itemDto.getItemType())) {
            throw new IllegalArgumentException("Invalid Item Type");
        }
        if (itemRepository.existsByName(itemDto.getName())) {
            throw new IllegalArgumentException("Item already exists");
        }
        Item itemToBeSaved = new Item();
        itemToBeSaved.setName(itemDto.getName());
        itemToBeSaved.setItemType(ItemType.valueOf(itemDto.getItemType()));

        return itemRepository.save(itemToBeSaved);

    }

    private boolean existItemType(String itemType) {
        for (ItemType it : ItemType.values()) {
            if (it.name().equals(itemType)) {
                return true;
            }
        }
        return false;
    }

}
