package Startup.example.Startup.Users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getSingleUser(String accountId) {
        return userRepository.findUserByAccountId(accountId);
    }

    public Optional<User> createNewUser(User user) {
        if (userRepository.findUserByAccountId(user.getAccountId()).isEmpty()) {
            userRepository.save(user);
        }
        return Optional.of(user);
    }


    public boolean isEligibleToLogin(String accountId) {
        Optional<User> userOpt = this.getSingleUser(accountId);
        return userOpt.isPresent();
    }
}
