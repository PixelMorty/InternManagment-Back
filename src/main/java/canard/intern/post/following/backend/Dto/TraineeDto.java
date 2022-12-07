package canard.intern.post.following.backend.Dto;


import canard.intern.post.following.backend.enums.Gender;
import canard.intern.post.following.backend.validators.DateLessThan;

import lombok.*;

import javax.validation.Constraint;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder
public class TraineeDto {

    private Integer id;
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    private Gender gender;
    @DateLessThan//TODO: replace with custim current date -18
    private LocalDate birthDate;

   @Pattern(regexp = "(\\+33|0)[0-9]{9}")
    private String phoneNumber;
    @Email@NotNull
    private  String email;
    private String poe;
}
