package attache.devs.popote.services;
import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.dtos.ResponseCustomerAndImageDTO;
import attache.devs.popote.exceptions.FileIsNotImageException;
import attache.devs.popote.exceptions.FileSizeNotValidException;
import attache.devs.popote.mappers.PopoteMapper;
import attache.devs.popote.models.Customer;
import attache.devs.popote.models.CustomerImage;
import attache.devs.popote.repositories.CustomerImageRepository;
import attache.devs.popote.repositories.CustomerRepository;
import attache.devs.popote.utils.FileParams;
import attache.devs.popote.utils.FileValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
@PropertySource("classpath:application.properties")
public class PopotoService {

    private final CustomerRepository customerRepository;
    private final CustomerImageRepository customerImageRepository;
    private final PopoteMapper popoteMapper;
    private final FileParams fileParams;
    private final FileValidator fileValidator;


    public ResponseCustomerAndImageDTO AddCustomerAndImage(PostCustomerDTO postCustomerDTO, MultipartFile image) throws IOException, FileIsNotImageException, FileSizeNotValidException {
        Customer customer = popoteMapper.fromPostCustomerDTO(postCustomerDTO);
        customerRepository.save(customer);
        ResponseCustomerAndImageDTO responseCustomerAndImageDTO = new ResponseCustomerAndImageDTO();
        responseCustomerAndImageDTO.setPostCustomerDTO(postCustomerDTO);

        if(image != null && !image.isEmpty()) {
            CustomerImage customerImage = saveImage(customer, image);
            responseCustomerAndImageDTO.setUrlImage(customerImage.getUrl());
        }

        return responseCustomerAndImageDTO;

    }

    private CustomerImage saveImage(Customer customer, MultipartFile image) throws FileIsNotImageException, FileSizeNotValidException, IOException {

        if (!fileValidator.isImage(image)) {
            throw new FileIsNotImageException("Le fichier n'est pas une image valide.");
        }

        if (!fileValidator.isFileSizeValid(image)) {
            throw new FileSizeNotValidException("La taille du fichier est supérieure à 2 MB.");
        }

        String uniqueFilename = "customer_" + customer.getId();
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String filename = StringUtils.cleanPath(uniqueFilename + '.'  + extension);
        Path imagePath = Paths.get(fileParams.customerDir());
        String url = fileParams.baseUrl() + fileParams.customerDir() + "/" + filename;

        if (!Files.exists(imagePath)) {
            Files.createDirectories(imagePath);
        }
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = imagePath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            CustomerImage customerImage = new CustomerImage();
            customerImage.setName(image.getOriginalFilename());
            customerImage.setCustomerPhone(customer.getPhoneNumber());
            customerImage.setUrl(url);
            customerImageRepository.save(customerImage);

            return customerImage;
        }
    }
}
