package inspringboot.capstone1.Service;

import inspringboot.capstone1.Model.User;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserService {

    @Getter
    ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        return false;
    }

    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                return true;
            }
        return false;
    }
}

