package attache.devs.popote.services;
import attache.devs.popote.dtos.CustomerDTO;
import attache.devs.popote.dtos.ResponseCustomerAndImageDTO;
import attache.devs.popote.exceptions.CustomerImageNotFoundException;
import attache.devs.popote.exceptions.CustomerNotFoundException;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PopotoService {

    private final CustomerRepository customerRepository;
    private final CustomerImageRepository customerImageRepository;
    private final PopoteMapper popoteMapper;
    private final FileParams fileParams;
    private final FileValidator fileValidator;


    public List<ResponseCustomerAndImageDTO> getAllCustomersWithImages() {
        List<Object[]> results = customerRepository.findAllCustomersWithImages();
        return getResponseCustomerAndImageDTOS(results);
    }

    public List<ResponseCustomerAndImageDTO> getCustomersById(Long customerId) {
        List<Object[]> results = customerRepository.findCustomerWithImagesById(customerId);
        return getResponseCustomerAndImageDTOS(results);

    }

    private List<ResponseCustomerAndImageDTO> getResponseCustomerAndImageDTOS(List<Object[]> results) {
        List<ResponseCustomerAndImageDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            Customer customer = (Customer) result[0];
            CustomerImage customerImage = (CustomerImage) result[1];
            CustomerDTO customerDTO = popoteMapper.fromCustomer(customer);

            ResponseCustomerAndImageDTO responseCustomerAndImageDTO = new ResponseCustomerAndImageDTO();
            responseCustomerAndImageDTO.setCustomerDTO(customerDTO);
            responseCustomerAndImageDTO.setUrlImage(customerImage.getUrl());
            dtos.add(responseCustomerAndImageDTO);
        }

        return dtos;
    }

    public ResponseCustomerAndImageDTO AddCustomerAndImage(CustomerDTO customerDTO, MultipartFile image) throws IOException, FileIsNotImageException, FileSizeNotValidException {
        Customer customer = popoteMapper.fromPostCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        customerDTO.setId(savedCustomer.getId());
        ResponseCustomerAndImageDTO responseCustomerAndImageDTO = new ResponseCustomerAndImageDTO();
        responseCustomerAndImageDTO.setCustomerDTO(customerDTO);

        if(image != null && !image.isEmpty()) {
            CustomerImage customerImage = saveImage(customer, image);
            responseCustomerAndImageDTO.setUrlImage(customerImage.getUrl());
        }

        return responseCustomerAndImageDTO;
    }



    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = popoteMapper.fromPostCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return popoteMapper.fromCustomer(savedCustomer);
    }


    public void deleteCustomerAndImage(Long id) throws CustomerNotFoundException {
        Customer customer = this.getCustomer(id);
        CustomerImage customerImage = this.getImage(customer.getPhoneNumber());
        if(customerImage != null) {
            customerImageRepository.delete(customerImage);
            deleteDirImageCustomer(customerImage.getName());
        }
        customerRepository.delete(customer);
    }


    public void updateImageCustomer(Long customerId, MultipartFile image) throws CustomerNotFoundException, IOException, FileIsNotImageException, FileSizeNotValidException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        saveImage(customer, image);
    }

    public void deleteImageCustomer(Long customerId) throws CustomerImageNotFoundException, CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        CustomerImage customerImage = customerImageRepository.findFirstByCustomerPhone(customer.getPhoneNumber());
        if(customerImage == null) {
            throw new CustomerImageNotFoundException("CustomerImage Not found");
        }
        customerImageRepository.delete(customerImage);
        deleteDirImageCustomer(customerImage.getName());
    }

    /*
         Private Class
     */

    private Customer getCustomer(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
    }

    private CustomerImage getImage(String customerPhone) {
        return customerImageRepository.findFirstByCustomerPhone(customerPhone);
    }


    private void deleteDirImageCustomer(String imageName) {
        String image = imageName.substring(0, imageName.lastIndexOf('.'));
        Path baseDir = Paths.get(fileParams.customerDir());
        try (Stream<Path> stream = Files.walk(baseDir)) {
            stream.filter(path -> path.getFileName().toString().startsWith(image))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ex) {
                            throw new RuntimeException("Failed to delete customer image: " + ex.getMessage(), ex);
                        }
                    });
        } catch (IOException ex) {
            throw new RuntimeException("Failed to delete customer images: " + ex.getMessage(), ex);
        }
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
            customerImage.setName(filename);
            customerImage.setCustomerPhone(customer.getPhoneNumber());
            customerImage.setUrl(url);
            customerImageRepository.save(customerImage);

            return customerImage;
        }
    }


}
