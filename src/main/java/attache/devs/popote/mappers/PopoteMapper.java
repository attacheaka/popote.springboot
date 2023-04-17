package attache.devs.popote.mappers;

import attache.devs.popote.dtos.CustomerDTO;
import attache.devs.popote.models.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PopoteMapper {

    public Customer fromPostCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }

    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }


    public CustomerDTO fromCustomerToPostCustomerDTO(Customer customer) {
        CustomerDTO customerDTO =new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }



}
