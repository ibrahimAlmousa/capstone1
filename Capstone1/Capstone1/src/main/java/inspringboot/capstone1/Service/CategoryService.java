package inspringboot.capstone1.Service;

import inspringboot.capstone1.Model.Category;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CategoryService {

    @Getter
    ArrayList<Category> categories = new ArrayList<>();


    public void addCategory(Category category){
        categories.add(category);
    }

    public boolean updateCategory(String id , Category category){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId().equals(id)){
                categories.set(i,category);
                return true ;
            }
        }
        return false ;
    }

    public boolean delete(String id ){
        for (Category c:categories){
            if(c.getId().equals(id)){
                categories.remove(c);
                return true;
            }
        }
        return false ;
    }





}
