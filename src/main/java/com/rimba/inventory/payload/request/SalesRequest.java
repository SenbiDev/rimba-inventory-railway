package com.rimba.inventory.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SalesRequest {

//    @JsonProperty("code_transaki")
//    String codeTransaksi;

//    @JsonProperty("tanggal_transaki")
//    String tanggalTransaksi;

    Long customer;

    Long[] item;

//    Integer qty;

//    @JsonProperty("total_diskon")
//    Integer totalDiskon;
//
//    @JsonProperty("total_bayar")
//    Integer totalBayar;
//
//    @JsonProperty("total_harga")
//    Integer totalHarga;
}
