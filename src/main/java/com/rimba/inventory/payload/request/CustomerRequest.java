package com.rimba.inventory.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class CustomerRequest {

    String nama;

    String contact;

    String email;

    String alamat;

    Integer diskon;

//    @JsonProperty("tipe_diskon")
    String tipeDiskon;

    MultipartFile ktp;
}
