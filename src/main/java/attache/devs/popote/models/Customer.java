package attache.devs.popote.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le prénom ne peut pas être vide.")
    private String firstName;

    @NotNull(message = "Le nom ne peut pas être vide.")
    private String lastName;

    @NotNull(message = "Le genre ne peut pas être vide.")
    private String gender;

    @NotNull(message = "La date de naissance ne peut pas être vide.")
    private Date birthDate;

    @NotNull(message = "Le lieu de naissance ne peut pas être vide.")
    private String birthPlace;

    @NotNull(message = "Le code de téléphone ne peut pas être vide.")
    private String phoneCode;

    @NotNull(message = "Le numéro de téléphone ne peut pas être vide.")
    @Column(unique = true, nullable = false)
    private String phoneNumber;
}

