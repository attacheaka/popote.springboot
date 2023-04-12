package attache.devs.popote.mappers;

import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.dtos.PostCustomerImageDTO;
import attache.devs.popote.models.Customer;
import attache.devs.popote.models.CustomerImage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PopoteMapper {

    public PostCustomerDTO fromCustomer(Customer customer){
        PostCustomerDTO postCustomerDTO=new PostCustomerDTO();
        BeanUtils.copyProperties(customer,postCustomerDTO);
        return  postCustomerDTO;
    }

    public Customer fromPostCustomerDTO(PostCustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }

    public PostCustomerImageDTO fromCustomerImage(CustomerImage customerImage){
        PostCustomerImageDTO postCustomerImageDTO =new PostCustomerImageDTO();
        BeanUtils.copyProperties(customerImage,postCustomerImageDTO);
        return  postCustomerImageDTO;
    }

    public CustomerImage fromPostCustomerImageDTO(PostCustomerDTO postCustomerDTO){
        CustomerImage customerImage = new CustomerImage();
        BeanUtils.copyProperties(postCustomerDTO,customerImage);
        return  customerImage;
    }


}
