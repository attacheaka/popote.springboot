package attache.devs.popote.controllers;

import attache.devs.popote.dtos.PostCustomerDTO;
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
    public PostCustomerDTO postCustomer(
            @ModelAttribute PostCustomerDTO postCustomerDTO,
            @RequestParam("image") MultipartFile image) throws IOException {
        popotoService.AddCustomerAndImage(postCustomerDTO, image);
        return postCustomerDTO;
    }

    // Update Image
    // Delete Image
    // Update Customer And Image
    // Get Customers and Image
    // Delete Customer and Image

    // Resolve Bug Image
}
