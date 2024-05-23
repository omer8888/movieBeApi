package Startup.example.Startup.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/showAll")
    private ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    private ResponseEntity<Optional<User>> getSingleUser(@PathVariable String accountId) {
        return new ResponseEntity<>(userService.getSingleUser(accountId), HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<Optional<User>> Create(@RequestBody User user){
        return new ResponseEntity<>(userService.createNewUser(user), HttpStatus.OK);
    }
}
