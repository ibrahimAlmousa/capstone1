package inspringboot.capstone1.controller;

import inspringboot.capstone1.ApiResponse.ApiResponse;
import inspringboot.capstone1.Model.MerchantStock;
import inspringboot.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/merchant-stock")
@AllArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks() {
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors) {

        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (merchantStockService.addMerchantStock(merchantStock))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Product ID or Merchant ID does not exist"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id,
                                                 @Valid @RequestBody MerchantStock merchantStock,
                                                 Errors errors) {

        if (errors.hasErrors())
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (merchantStockService.updateMerchantStock(id, merchantStock))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id) {
        if (merchantStockService.deleteMerchantStock(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
    }

    @PutMapping("/add-more-stocks/{productid}/{merchantid}/{amount}")
    public ResponseEntity<?> addMoreStocks(@PathVariable String productid,@PathVariable String merchantid,@PathVariable int amount){
        if(merchantStockService.addMoreStocks(productid,merchantid,amount)){
            return ResponseEntity.status(200).body(new ApiResponse("added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("not found"));
    }

    @PutMapping("/buy/{userID}/{productID}/{merchantID}")
    public ResponseEntity<?> buyProduct(@PathVariable String userID, @PathVariable String productID, @PathVariable String merchantID){
        if(merchantStockService.buyProduct(userID, productID, merchantID).getMessage().equals("success buy"))
            return ResponseEntity.status(200).body(merchantStockService.buyProduct(userID, productID, merchantID));

        return ResponseEntity.status(400).body(merchantStockService.buyProduct(userID, productID, merchantID));
    }

    @GetMapping("/get-almost-over/{merchantID}")
    public ResponseEntity<?> getAlmostOver(@PathVariable String merchantID){
        return ResponseEntity.status(200).body(merchantStockService.getAlmostOver(merchantID));
    }

    @GetMapping("/recommended-by-merchant/{merchantID}")
    public ResponseEntity<?> getRecommendedByMerchant(@PathVariable String merchantID){

        if(merchantStockService.getMerchantProducts(merchantID).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No products found for this merchant"));

        return ResponseEntity.status(200).body(merchantStockService.getMerchantProducts(merchantID));
    }


    @PutMapping("/buy-with-discount/{userID}/{productID}/{merchantID}/{code}")
    public ResponseEntity<?> buyProductWithDiscount(@PathVariable String userID, @PathVariable String productID, @PathVariable String merchantID, @PathVariable String code){
        if(merchantStockService.buyProductWithDiscount(userID, productID, merchantID, code).getMessage().startsWith("success"))
            return ResponseEntity.status(200).body(merchantStockService.buyProductWithDiscount(userID, productID, merchantID, code));

        return ResponseEntity.status(400).body(merchantStockService.buyProductWithDiscount(userID, productID, merchantID, code));
    }




}
