package inspringboot.capstone1.controller;

import inspringboot.capstone1.ApiResponse.ApiResponse;
import inspringboot.capstone1.Model.Product;
import inspringboot.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (productService.addProduct(product))
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category ID does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (productService.updateProduct(id, product))
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        if (productService.deleteProduct(id))
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));
    }

    @GetMapping("/sort-by-price")
    public ResponseEntity<?> sortByPrice(){
        return ResponseEntity.status(200).body(productService.sortByPrice());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchProducts(@PathVariable String name){
        if(productService.searchProducts(name).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No matching products found"));

        return ResponseEntity.status(200).body(productService.searchProducts(name));
    }

    @GetMapping("/range/{min}/{max}")
    public ResponseEntity<?> getProductsByRange(@PathVariable double min, @PathVariable double max){


        if( productService.getProductsByPriceRange(min, max).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No products found in this range"));

        return ResponseEntity.status(200).body( productService.getProductsByPriceRange(min, max));
    }

}

