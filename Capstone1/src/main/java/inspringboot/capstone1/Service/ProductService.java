package inspringboot.capstone1.Service;

import inspringboot.capstone1.Model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProductService {

    private final CategoryService categoryService;

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {
        for (int i = 0; i < categoryService.categories.size(); i++) {
            if (product.getCategoryID().equals(categoryService.categories.get(i).getId())) {
                products.add(product);
                return true;
            }
        }
        return false;
    }

    public boolean updateProduct(String id, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> sortByPrice(){
        ArrayList<Product> result = new ArrayList<>(products);

        for (int i = 0; i < result.size(); i++)
            for (int j = 0; j < result.size() - 1; j++)
                if(result.get(j).getPrice() > result.get(j + 1).getPrice()){
                    Product temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }

        return result;
    }

    public ArrayList<Product> searchProducts(String name){
        ArrayList<Product> result = new ArrayList<>();

        for(int i = 0 ; i < products.size() ; i++)
            if(products.get(i).getName().toLowerCase().contains(name.toLowerCase()))
                result.add(products.get(i));

        return result;
    }

    public ArrayList<Product> getProductsByPriceRange(double min, double max){

        ArrayList<Product> result = new ArrayList<>();

        for (int i = 0; i < products.size(); i++)
            if (products.get(i).getPrice() >= min && products.get(i).getPrice() <= max)
                result.add(products.get(i));

        for (int i = 0; i < result.size(); i++)
            for (int j = 0; j < result.size() - 1; j++)
                if (result.get(j).getPrice() > result.get(j + 1).getPrice()) {

                    Product temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }

        return result;
    }

}
