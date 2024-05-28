package Startup.example.Startup.Users;

import Startup.example.Startup.SubscriptionCredit.SubscriptionCredit;
import Startup.example.Startup.SubscriptionCredit.SubscriptionCreditRepository;
import Startup.example.Startup.SubscriptionCredit.SubscriptionCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionCreditRepository subscriptionCreditRepository;

    @Autowired
    private SubscriptionCreditService subscriptionCreditService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getSingleUser(String accountId) {
        return userRepository.findUserByAccountId(accountId);
    }

    public Optional<User> createNewUser(User user) {
        //on new user saves to db
        if (userRepository.findUserByAccountId(user.getAccountId()).isEmpty()) {
            userRepository.save(user);
            SubscriptionCredit subscriptionCredit = new SubscriptionCredit(null,user.getAccountId(),-1,1,1,new Date());
            subscriptionCreditRepository.save(subscriptionCredit);
        }
        return Optional.of(user);
    }

}
