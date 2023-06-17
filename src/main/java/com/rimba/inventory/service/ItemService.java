package com.rimba.inventory.service;

import com.rimba.inventory.models.Item;
import com.rimba.inventory.payload.request.ItemRequest;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    List<Item> getAll();
    Item get(Long id);
    Item create(ItemRequest itemRequest) throws IOException;
    Item update(Long id, ItemRequest itemRequest) throws IOException;
    String delete(Long id);
    List<Item> saveAll(Iterable<Item> items);
}
