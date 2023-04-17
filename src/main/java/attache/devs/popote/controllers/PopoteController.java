package attache.devs.popote.controllers;

import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.dtos.ResponseCustomerAndImageDTO;
import attache.devs.popote.exceptions.CustomerImageNotFoundException;
import attache.devs.popote.exceptions.CustomerNotFoundException;
import attache.devs.popote.exceptions.FileIsNotImageException;
import attache.devs.popote.exceptions.FileSizeNotValidException;
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
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException, FileIsNotImageException, FileSizeNotValidException {

        return popotoService.AddCustomerAndImage(postCustomerDTO, image);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException, CustomerImageNotFoundException, IOException {
        popotoService.deleteCustomerAndImage(customerId);
    }

    @PutMapping("/customers/{customerId}/image")
    public void updateImageCustomer(@PathVariable Long customerId, @RequestParam(value = "image") MultipartFile image) throws IOException, CustomerNotFoundException, FileIsNotImageException, FileSizeNotValidException {
        popotoService.updateImageCustomer(customerId, image);
    }

    @DeleteMapping("/customers/{customerId}/image")
    public void deleteImageCustomer(@PathVariable Long customerId) throws CustomerImageNotFoundException, IOException, CustomerNotFoundException {
        popotoService.deleteImageCustomer(customerId);
    }





    // Add Image by Cusomer /customers/{id}/image POST (1)
     /*
           - Get CustomerById
           - Customer phoneNumber And Post Image
      */

    // Update Image /customers/{Id}  (5)
    /*
         Call update customer
         Call update image BD
         Call Update File Image Upload
     */

    // delete Image /image/{Id}  (4)
    // -> toDelete Customers
    // -> fileUpload

    // Get Customers and Image (2)
    /*
       - Affiche les infos customer
       - Affiche les infos image
     */

    // GetById Customers and Image /customers/{Id} (3)
    // Delete Customers
    // -> Call delete Image By Cusomter

}
