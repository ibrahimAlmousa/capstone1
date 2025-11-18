package inspringboot.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "ID must contain only numbers")
    @Size(min = 3, max = 10, message = "ID must be between 3 and 10 digits")
    private String id;

    @NotEmpty(message = "Product ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Product ID must contain only numbers")
    private String productid;

    @NotEmpty(message = "Merchant ID cannot be empty")
    @Pattern(regexp = "^[0-9]+$", message = "Merchant ID must contain only numbers")
    private String merchantid;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 10, message = "Stock must be at least 10")
    private int stock;
}
