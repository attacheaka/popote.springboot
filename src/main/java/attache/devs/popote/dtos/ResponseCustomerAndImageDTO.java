package attache.devs.popote.dtos;

import lombok.Data;

@Data
public class ResponseCustomerAndImageDTO {
    private PostCustomerDTO postCustomerDTO;
    private String urlImage;
}
