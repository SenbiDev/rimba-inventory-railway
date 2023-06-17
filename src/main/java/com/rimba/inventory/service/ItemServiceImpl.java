package com.rimba.inventory.service;

import com.rimba.inventory.exception.ResourceNotFoundException;
import com.rimba.inventory.models.Item;
import com.rimba.inventory.enums.ItemEnum;
import com.rimba.inventory.payload.request.ItemRequest;
import com.rimba.inventory.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item get(Long id) {
        return itemRepository.getReferenceById(id);
    }

    @Override
    public Item create(ItemRequest itemRequest) throws IOException {
        ItemEnum itemEnum;

        if (itemRequest.getUnit().equals("kg")) {
            itemEnum = ItemEnum.KG;
        } else {
            itemEnum = ItemEnum.PCS;
        }

        Item newItem = new Item(
                itemRequest.getNamaItem(),
                itemEnum,
                itemRequest.getStok(),
                itemRequest.getHargaSatuan(),
                itemRequest.getGambar().getBytes()
        );

        return itemRepository.save(newItem);
    }

    @Override
    public Item update(Long id, ItemRequest itemRequest) throws IOException {
        Item existingItem = itemRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("item with id %s not found", id))
                );

        ItemEnum itemEnum;

        if (itemRequest.getUnit() != null && itemRequest.getUnit().equals("kg")) {
            itemEnum = ItemEnum.KG;
        } else {
            itemEnum = ItemEnum.PCS;
        }

        existingItem.setItemName(itemRequest.getNamaItem() == null ? existingItem.getItemName() : itemRequest.getNamaItem());
        existingItem.setUnit(itemRequest.getUnit() == null ? existingItem.getUnit() : itemEnum);
        existingItem.setStock(itemRequest.getStok() == null ? existingItem.getStock() : itemRequest.getStok());
        existingItem.setUnitPrice(itemRequest.getHargaSatuan() == null ? existingItem.getUnitPrice() : itemRequest.getHargaSatuan());
        existingItem.setItemImage(itemRequest.getGambar() == null ? existingItem.getItemImage() : itemRequest.getGambar().getBytes());

        return itemRepository.save(existingItem);
    }

    @Override
    public String delete(Long id) {
        itemRepository.deleteById(id);

        return "deleted";
    }

    @Override
    public List<Item> saveAll(Iterable<Item> items) {
        return itemRepository.saveAll(items);
    }
}
