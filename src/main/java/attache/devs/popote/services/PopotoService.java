package attache.devs.popote.services;
import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.dtos.ResponseCustomerAndImageDTO;
import attache.devs.popote.mappers.PopoteMapper;
import attache.devs.popote.models.Customer;
import attache.devs.popote.models.CustomerImage;
import attache.devs.popote.repositories.CustomerImageRepository;
import attache.devs.popote.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PopotoService {

    private final CustomerRepository customerRepository;
    private final CustomerImageRepository customerImageRepository;
    private final PopoteMapper popoteMapper;


    public ResponseCustomerAndImageDTO AddCustomerAndImage(PostCustomerDTO postCustomerDTO, MultipartFile image) throws IOException {
        Customer customer = popoteMapper.fromPostCustomerDTO(postCustomerDTO);
        CustomerImage customerImage = saveImage(postCustomerDTO, image);
        customerRepository.save(customer);

        ResponseCustomerAndImageDTO responseCustomerAndImageDTO = new ResponseCustomerAndImageDTO();
        responseCustomerAndImageDTO.setPostCustomerDTO(postCustomerDTO);
        responseCustomerAndImageDTO.setUrlImage(customerImage.getUrl());

        return responseCustomerAndImageDTO;
    }

    private CustomerImage saveImage(PostCustomerDTO postCustomerDTO, MultipartFile image) throws IOException {

        String filename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uniqueFilename = "image_" + System.currentTimeMillis();
        String uploadDir = "upload/";
        Path imagePath = Paths.get(uploadDir, uniqueFilename);
        String url = uploadDir + uniqueFilename + "/" + filename;

        if (!Files.exists(imagePath)) {
            Files.createDirectories(imagePath);
        }
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = imagePath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            CustomerImage customerImage = new CustomerImage();
            customerImage.setName(image.getOriginalFilename());
            customerImage.setCustomerPhone(postCustomerDTO.getPhoneNumber());
            customerImage.setUrl(url);
            customerImageRepository.save(customerImage);

            return customerImage;
        }
    }
}
