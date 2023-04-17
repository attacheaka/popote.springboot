package attache.devs.popote.dtos;

import lombok.Data;

@Data
public class ResponseCustomerAndImageDTO {
    private CustomerDTO customerDTO;
    private String urlImage;
}
