package inspringboot.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "ID must contain only numbers")
    @Size(min = 3, max = 10, message = "ID must be between 3 and 10 digits")
    private String id;

    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters")
    @Size(min = 3, max = 25, message = "Name must be at least 3 characters")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 2 decimal places")
    private double price;

    @NotEmpty(message = "Category ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Category ID must contain only numbers")
    @Size(min = 1, max = 10, message = "Category ID must be between 1 and 10 digits")
    private String categoryID;
}
