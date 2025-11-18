package inspringboot.capstone1.controller;

import inspringboot.capstone1.ApiResponse.ApiResponse;
import inspringboot.capstone1.Model.Merchant;
import inspringboot.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@AllArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants() {
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,
                                            @Valid @RequestBody Merchant merchant,
                                            Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (merchantService.updateMerchant(id, merchant))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        if (merchantService.deleteMerchant(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }
}
