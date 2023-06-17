package com.rimba.inventory.dummy;

import com.rimba.inventory.models.Item;
import com.rimba.inventory.enums.ItemEnum;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class DummyItem {

    public List<Item> get() {
        LinkedList<File> fileList = new LinkedList<>(Arrays.asList(
                new File("src/main/resources/static/images/dummy/ASUS_TUF_A15_FA506QM.jpg"),
                new File("src/main/resources/static/images/dummy/HP_OMEN_Laptop_16_n0045AX.jpg"),
                new File("src/main/resources/static/images/dummy/MSI_STEALTH_15M_B12UE_017.png"),
                new File("src/main/resources/static/images/dummy/Lenovo_Legion_5_Pro_16IAH7H_5NID.jpg"),
                new File("src/main/resources/static/images/dummy/Samsung_Galaxy_M14_5G.jpg"),
                new File("src/main/resources/static/images/dummy/Samsung_Galaxy_A54 5G.jpg")
        ));

        LinkedList<byte[]> imageDataList = new LinkedList<>(fileList.stream().map(file -> {
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList());

        LinkedList<Item> itemList = new LinkedList<>(Arrays.asList(
                new Item(
                        "Laptop ASUS TUF A15 FA506QM GeForce RTX™ 3060 RYZEN 7-5800H 16G 512GB OHS W11",
                        ItemEnum.KG,
                        100,
                        15_000_000,
                        imageDataList.get(0)
                ),
                new Item(
                        "Laptop HP OMEN Laptop 16 n0045AX Ryzen 7-6800H RTX 3060 16GB 512GB SSD 144Hz - Mica Silver",
                        ItemEnum.KG,
                        100,
                        20_500_000,
                        imageDataList.get(1)
                ),
                new Item(
                        "Laptop MSI STEALTH 15M B12UE 017 I7-1260P 16GB 1TB SSD RTX 3060 6GB FHD IPS",
                        ItemEnum.KG,
                        100,
                        18_650_000,
                        imageDataList.get(2)
                ),
                new Item(
                        "Laptop Lenovo Legion 5 Pro 16IAH7H 5NID | i7 12700H 16GB 1TB RTX 3060 W11 OHS",
                        ItemEnum.KG,
                        100,
                        24_100_000,
                        imageDataList.get(3)
                ),
                new Item(
                        "Hp Samsung Galaxy M14 5G 6/128GB",
                        ItemEnum.PCS,
                        30,
                        3_000_000,
                        imageDataList.get(4)
                ),
                new Item(
                        "Hp Samsung Galaxy A54 5G 8/128",
                        ItemEnum.PCS,
                        30,
                        5_400_000,
                        imageDataList.get(5)
                )
        ));
        return itemList;
    }
}
