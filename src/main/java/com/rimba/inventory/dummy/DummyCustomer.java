package com.rimba.inventory.dummy;

import com.rimba.inventory.models.Customer;
import com.rimba.inventory.enums.CustomerEnum;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class DummyCustomer {

    public List<Customer> get() throws IOException {
        File file = new File("src/main/resources/static/images/dummy/ktp_dummy.png");
        byte[] imageData = Files.readAllBytes(file.toPath());

        return List.of(
                new Customer(
                        "Mrs Marilce da Mota",
                        "(61) 8253-0455",
                        "marilce.damota@example.com",
                        "João Pessoa, Tocantins, Brazil",
                        20,
                        CustomerEnum.PERSENTASE,
                        imageData
                ),
                new Customer(
                        "Ms Samantha Arnold",
                        "016974 01162",
                        "samantha.arnold@example.com",
                        "Newry, Essex, United Kingdom",
                        25,
                        CustomerEnum.PERSENTASE,
                        imageData
                ),
                new Customer(
                        "Mr Alexander Brown",
                        "(047)-497-9143",
                        "alexander.brown@example.com",
                        "Whanganui, Tasman, New Zealand",
                        30,
                        CustomerEnum.PERSENTASE,
                        imageData
                ),
                new Customer(
                        "Mrs Ava Bélanger",
                        "F97 O06-2480",
                        "ava.belanger@example.com",
                        "Kingston, Prince Edward Island, Canada",
                        20_000,
                        CustomerEnum.FIX_DISKON,
                        imageData
                ),
                new Customer(
                        "Monsieur Tristan Laurent",
                        "076 162 43 03",
                        "tristan.laurent@example.com",
                        "Chexbres, Bern, Switzerland",
                        25_000,
                        CustomerEnum.FIX_DISKON,
                        imageData
                ),
                new Customer(
                        "Mr Carter Hansen",
                        "01-7633-7890",
                        "carter.hansen@example.com",
                        "Hobart, New South Wales, Australia",
                        30_000,
                        CustomerEnum.FIX_DISKON,
                        imageData
                )
        );
    }
}
