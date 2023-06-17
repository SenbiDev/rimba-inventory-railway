package com.rimba.inventory.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class ItemRequest {

//    @JsonProperty("nama_item")
    String namaItem;

    String unit;

    Integer stok;

//    @JsonProperty("harga_satuan")
    Integer hargaSatuan;

    MultipartFile gambar;
}
