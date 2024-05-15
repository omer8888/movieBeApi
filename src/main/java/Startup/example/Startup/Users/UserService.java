package Startup.example.Startup.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return  userRepository.findAll();
    }

    public Optional<User> getSingleUser(String accountId) {
        return userRepository.findUserByAccountId(accountId);
    }

}
