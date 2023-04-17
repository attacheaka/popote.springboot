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

    @PutMapping("/customers/{customerId}")
    public PostCustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody PostCustomerDTO postCustomerDTO){
        postCustomerDTO.setId(customerId);
        return popotoService.updateCustomer(postCustomerDTO);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        popotoService.deleteCustomerAndImage(customerId);
    }


    @PutMapping("/customers/{customerId}/image")
    public void updateImageCustomer(@PathVariable Long customerId, @RequestParam(value = "image") MultipartFile image) throws IOException, CustomerNotFoundException, FileIsNotImageException, FileSizeNotValidException {
        popotoService.updateImageCustomer(customerId, image);
    }

    @DeleteMapping("/customers/{customerId}/image")
    public void deleteImageCustomer(@PathVariable Long customerId) throws CustomerImageNotFoundException, CustomerNotFoundException {
        popotoService.deleteImageCustomer(customerId);
    }


}
