package inspringboot.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "ID must contain only numbers")
    @Size(min = 3, max = 10, message = "ID must be between 3 and 10 digits")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters")
    @Size(min = 3, max = 25, message = "Name must be at least 3 characters")
    private String name;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 7, message = "Password must be more than 6 characters long")
    @Pattern( regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$" , message = "Password must contain both letters and digits")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either 'Admin' or 'Customer'")
    private String role;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance cannot be negative")
    private double balance;
}
