package attache.devs.popote.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;

    private String birthPlace;
    private String phoneCode;
    private String phoneNumber;
}
