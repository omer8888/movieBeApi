package Startup.example.Startup.Users;

import Startup.example.Startup.Users.login.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Claims;


import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/showAll")
    private ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    private ResponseEntity<Optional<User>> getSingleUser(@PathVariable String accountId) {
        return new ResponseEntity<>(userService.getSingleUser(accountId), HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<Optional<User>> Create(@RequestBody User user) {
        return new ResponseEntity<>(userService.createNewUser(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<Optional<User>> loginUser(@RequestParam String accountId, HttpServletResponse response) {
        Optional<User> user = userService.getSingleUser(accountId);
        if (user.isPresent()) {
            setLoginSessionCookie(accountId, response);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/signupOrLogin")
    public ResponseEntity<Optional<User>> create(@RequestBody User requestUser, HttpServletResponse response) {
        Optional<User> existingUser = userService.getSingleUser(requestUser.getAccountId());

        // Creating new user
        if (existingUser.isEmpty()) {
            existingUser = userService.createNewUser(requestUser);
        }

        // Logging in
        setLoginSessionCookie(requestUser.getAccountId(), response);

        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }

    private void setLoginSessionCookie(String accountId, HttpServletResponse response) {
        String jwt = jwtService.generateToken(accountId); // Generate a JWT
        Cookie cookie = new Cookie("ATUS", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(cookie);
    }

    private void removeLoginSessionCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("ATUS", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Setting Max-Age to 0 to delete the cookie
        response.addCookie(cookie);
    }


    @PostMapping("/logout")
    private ResponseEntity<Optional<User>> logoutUser(HttpServletResponse response) {
        removeLoginSessionCookie(response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@CookieValue(value = "ATUS", defaultValue = "") String token) {
        Claims claims = jwtService.validateToken(token);
        if (claims != null) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}
