package attache.devs.popote.mappers;

import attache.devs.popote.dtos.PostCustomerDTO;
import attache.devs.popote.models.Customer;
import attache.devs.popote.models.CustomerImage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PopoteMapper {

    public Customer fromPostCustomerDTO(PostCustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return  customer;
    }


}
