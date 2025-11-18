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


    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchProducts(@PathVariable String name){
        if(productService.searchProducts(name).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No matching products found"));

        return ResponseEntity.status(200).body(productService.searchProducts(name));
    }

    @GetMapping("/range/{categoryID}/{min}/{max}")
    public ResponseEntity<?> getProductsByRange(
            @PathVariable String categoryID,
            @PathVariable double min,
            @PathVariable double max){

        if(productService.getProductsByPriceRange(categoryID, min, max).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No products found in this range for this category"));

        return ResponseEntity.status(200).body(productService.getProductsByPriceRange(categoryID, min, max));
    }


    @GetMapping("/sort-by-price/{categoryID}")
    public ResponseEntity<?> sortByPrice(@PathVariable String categoryID){
        return ResponseEntity.status(200).body(productService.sortByPrice(categoryID));
    }



}

