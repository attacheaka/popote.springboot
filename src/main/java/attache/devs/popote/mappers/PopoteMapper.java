package attache.devs.popote.mappers;

import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.models.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PopoteMapper {

    public Customer fromPostCustomerDTO(PostCustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }

    public PostCustomerDTO fromCustomer(Customer customer) {
        PostCustomerDTO postCustomerDTO = new PostCustomerDTO();
        BeanUtils.copyProperties(customer,postCustomerDTO);
        return  postCustomerDTO;
    }


    public  PostCustomerDTO fromCustomerToPostCustomerDTO(Customer customer) {
        PostCustomerDTO postCustomerDTO =new PostCustomerDTO();
        BeanUtils.copyProperties(customer,postCustomerDTO);
        return  postCustomerDTO;
    }



}
