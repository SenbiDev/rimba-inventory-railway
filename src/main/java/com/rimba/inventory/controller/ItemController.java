package com.rimba.inventory.controller;

import com.rimba.inventory.dummy.DummyItem;
import com.rimba.inventory.models.Item;
import com.rimba.inventory.payload.request.ItemRequest;
import com.rimba.inventory.payload.response.ItemData;
import com.rimba.inventory.payload.response.ItemResponse;
import com.rimba.inventory.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/items")
@PreAuthorize("hasRole('ADMIN')")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    DummyItem dummyItem;

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Item item = itemService.get(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(item.getItemImage());
    }

    @GetMapping("")
    ResponseEntity<ItemResponse> getAll() {
        List<Item> getAllItem = itemService.getAll();
        List<ItemData> itemDataList = getAllItem.stream().map((data) -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/")
                    .path("items/")
                    .path("image/")
                    .path(String.valueOf(data.getId()))
                    .toUriString();

            ItemData itemData = new ItemData(
                    data.getId(),
                    data.getItemName(),
                    data.getUnit(),
                    data.getStock(),
                    data.getUnitPrice(),
                    fileDownloadUri
            );

            return itemData;
        }).toList();


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ItemResponse(HttpStatus.OK.value(), "success", itemDataList)
                );
    }

    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<ItemResponse> create(@ModelAttribute ItemRequest itemRequest) throws IOException {
        Item item = itemService.create(itemRequest);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/")
                .path("items/")
                .path("image/")
                .path(String.valueOf(item.getId()))
                .toUriString();

        ItemData itemData = new ItemData(
                item.getId(),
                item.getItemName(),
                item.getUnit(),
                item.getStock(),
                item.getUnitPrice(),
                fileDownloadUri
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ItemResponse<>(
                        HttpStatus.CREATED.value(),
                        "success",
                        itemData
                ));
    }

    @RequestMapping(path = "/{id}", method = PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<ItemResponse> update(
            @PathVariable Long id,
            @ModelAttribute ItemRequest itemRequest
    ) throws IOException {
        Item item = itemService.update(id, itemRequest);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/")
                .path("items/")
                .path("image/")
                .path(String.valueOf(item.getId()))
                .toUriString();

        ItemData itemData = new ItemData(
                item.getId(),
                item.getItemName(),
                item.getUnit(),
                item.getStock(),
                item.getUnitPrice(),
                fileDownloadUri
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ItemResponse(
                        HttpStatus.OK.value(),
                        "success",
                        itemData
                ));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ItemResponse> delete(@PathVariable Long id) {
        String message = itemService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ItemResponse(
                        HttpStatus.OK.value(),
                        message,
                        null
                ));
    }

    @PostMapping("/save-all")
    ResponseEntity<ItemResponse> saveAll() throws IOException {
        List<Item> itemList = dummyItem.get();
        List<Item> items = itemService.saveAll(itemList);
        List<ItemData> itemDataList = items.stream().map((data) -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/")
                    .path("items/")
                    .path("image/")
                    .path(String.valueOf(data.getId()))
                    .toUriString();

            ItemData itemData = new ItemData(
                    data.getId(),
                    data.getItemName(),
                    data.getUnit(),
                    data.getStock(),
                    data.getUnitPrice(),
                    fileDownloadUri
            );

            return itemData;
        }).toList();


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ItemResponse(HttpStatus.CREATED.value(), "success", itemDataList)
                );
    }
}
