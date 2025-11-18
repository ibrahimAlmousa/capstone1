package inspringboot.capstone1.controller;

import inspringboot.capstone1.ApiResponse.ApiResponse;
import inspringboot.capstone1.Model.User;
import inspringboot.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {

        if (errors.hasErrors())
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        userService.addUser(user);
        return ResponseEntity.status(200)
                .body(new ApiResponse("User added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        @Valid @RequestBody User user,
                                        Errors errors) {

        if (errors.hasErrors())
            return ResponseEntity.status(400)
                    .body(new ApiResponse(errors.getFieldError().getDefaultMessage()));

        if (userService.updateUser(id, user))
            return ResponseEntity.status(200)
                    .body(new ApiResponse("User updated successfully"));

        return ResponseEntity.status(400)
                .body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (userService.deleteUser(id))
            return ResponseEntity.status(200)
                    .body(new ApiResponse("User deleted successfully"));

        return ResponseEntity.status(400)
                .body(new ApiResponse("User not found"));
    }
}
