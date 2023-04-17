package attache.devs.popote.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDate;
    private String birthPlace;
    private String phoneCode;
    private String phoneNumber;
}
