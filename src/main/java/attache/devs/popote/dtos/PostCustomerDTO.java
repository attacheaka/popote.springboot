package attache.devs.popote.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PostCustomerDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String birthPlace;
    private String phoneCode;
    private String phoneNumber;
}
