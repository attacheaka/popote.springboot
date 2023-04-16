package attache.devs.popote.controllers;

import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.dtos.ResponseCustomerAndImageDTO;
import attache.devs.popote.services.PopotoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class PopoteController {

    private final PopotoService popotoService;

    @PostMapping(value = "/customers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseCustomerAndImageDTO postCustomer(
            @ModelAttribute PostCustomerDTO postCustomerDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        return popotoService.AddCustomerAndImage(postCustomerDTO, image);
    }

    // Update Image /customers/{Id}
    /*
         Call update customer
         Call update image
     */
    // delete Image /image/{Id}

    // Get Customers and Image
    /*
       - Affiche les infos customer
       - Affiche les infos image
     */

    // GetById Customers and Image /customers/{Id}
    // Delete Customers
    // -> Call delete Image By Cusomter

    // Resolve bug Image
}
