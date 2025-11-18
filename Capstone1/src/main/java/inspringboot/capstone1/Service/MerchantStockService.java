package inspringboot.capstone1.Service;

import inspringboot.capstone1.ApiResponse.ApiResponse;
import inspringboot.capstone1.Model.MerchantStock;
import inspringboot.capstone1.Model.Product;
import inspringboot.capstone1.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MerchantStockService {

    private final ProductService productService;
    private final MerchantService merchantService;
    private final UserService userService ;


    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    public boolean addMerchantStock(MerchantStock merchantStock) {

        boolean productExists = false;
        boolean merchantExists = false;

        for (int i = 0; i < productService.products.size(); i++)
            if (productService.products.get(i).getId().equals(merchantStock.getProductid()))
                productExists = true;

        for (int i = 0; i < merchantService.merchants.size(); i++)
            if (merchantService.merchants.get(i).getId().equals(merchantStock.getMerchantid()))
                merchantExists = true;

        if (!productExists || !merchantExists)
            return false;

        merchantStocks.add(merchantStock);
        return true;
    }

    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++)
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        return false;
    }

    public boolean deleteMerchantStock(String id) {
        for (int i = 0; i < merchantStocks.size(); i++)
            if (merchantStocks.get(i).getId().equals(id)) {
                merchantStocks.remove(i);
                return true;
            }
        return false;
    }

    public boolean addMoreStocks(String productid, String merchantid, int amount) {

        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getProductid().equals(productid)  &&  merchantStocks.get(i).getMerchantid().equals(merchantid)) {
                int newStock = merchantStocks.get(i).getStock() + amount;
                merchantStocks.get(i).setStock(newStock);
                return true;
            }
        }

        return false;
    }

    public ApiResponse buyProduct(String userID , String productID , String merchantID){

        User user = null;
        for(int i = 0 ; i < userService.users.size() ; i++)
            if(userService.users.get(i).getId().equals(userID)){
                user = userService.users.get(i);
                break;
            }
        if(user == null)
            return new ApiResponse("user id not found");



        Product product = null;
        for(int i = 0 ; i < productService.products.size() ; i++)
            if(productService.products.get(i).getId().equals(productID)){
                product = productService.products.get(i);
                break;
            }
        if(product == null)
            return new ApiResponse("product id not found");



        MerchantStock merchantStock = null;
        for(int i = 0 ; i < merchantStocks.size() ; i++)
            if(merchantStocks.get(i).getProductid().equals(productID)
                    && merchantStocks.get(i).getMerchantid().equals(merchantID)){
                merchantStock = merchantStocks.get(i);
                break;
            }
        if(merchantStock == null)
            return new ApiResponse("merchant id not found");



        if(merchantStock.getStock() <= 0)
            return new ApiResponse("out of stock");

        if(user.getBalance() < product.getPrice())
            return new ApiResponse("the balance not allowed");

        merchantStock.setStock(merchantStock.getStock() - 1);
        user.setBalance(user.getBalance() - product.getPrice());

        return new ApiResponse("success buy");
    }


    public ArrayList<MerchantStock> getAlmostOver(){
        ArrayList<MerchantStock> result = new ArrayList<>();
        for(MerchantStock m : merchantStocks) {
            if (m.getStock()<10){
                result.add(m);
            }
        }
        return result ;
    }

    public ArrayList<Product> getMerchantProducts(String merchantID){
        ArrayList<Product> result = new ArrayList<>();

        for(int i = 0 ; i < merchantStocks.size() ; i++) {
            if (merchantStocks.get(i).getMerchantid().equals(merchantID)) {
                for (int j = 0; j < productService.products.size(); j++)
                    if (productService.products.get(j).getId().equals(merchantStocks.get(i).getProductid()))
                        result.add(productService.products.get(j));
            }
        }
        return result;
    }


    public ApiResponse buyProductWithDiscount(String userID , String productID , String merchantID , String discountCode){

        User user = null;
        for(int i = 0 ; i < userService.users.size() ; i++)
            if(userService.users.get(i).getId().equals(userID)){
                user = userService.users.get(i);
                break;
            }
        if(user == null)
            return new ApiResponse("user id not found");




        Product product = null;
        for(int i = 0 ; i < productService.products.size() ; i++)
            if(productService.products.get(i).getId().equals(productID)){
                product = productService.products.get(i);
                break;
            }
        if(product == null)
            return new ApiResponse("product id not found");



        MerchantStock merchantStock = null;
        for(int i = 0 ; i < merchantStocks.size() ; i++)
            if(merchantStocks.get(i).getProductid().equals(productID) && merchantStocks.get(i).getMerchantid().equals(merchantID)){
                merchantStock = merchantStocks.get(i);
                break;
            }
        if(merchantStock == null)
            return new ApiResponse("merchant id not found");

        if(merchantStock.getStock() <= 0)
            return new ApiResponse("out of stock");

        double discount = 0.0;
        if(discountCode != null){
            if(discountCode.equals("10"))
                discount = 0.10;
            else if(discountCode.equals("20"))
                discount = 0.20;
            else if(discountCode.equals("50"))
                discount = 0.50;
            else
                return new ApiResponse("invalid discount code");
        }

        double finalPrice = product.getPrice() - (product.getPrice() * discount);

        if(user.getBalance() < finalPrice)
            return new ApiResponse("the balance not allowed");

        merchantStock.setStock(merchantStock.getStock() - 1);
        user.setBalance(user.getBalance() - finalPrice);

        return new ApiResponse("success buy");
    }

}
