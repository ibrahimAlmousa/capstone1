package inspringboot.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "ID must contain only numbers")
    @Size(min = 3, max = 10, message = "ID must be between 3 and 10 digits")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters")
    @Size(min = 3, max = 25, message = "Name must be at least 3 characters")
    private String name;
}
