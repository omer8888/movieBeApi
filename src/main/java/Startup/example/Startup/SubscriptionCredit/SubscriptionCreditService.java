package Startup.example.Startup.SubscriptionCredit;

import Startup.example.Startup.Users.User;
import Startup.example.Startup.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionCreditService {

    @Autowired
    private SubscriptionCreditRepository subscriptionCreditRepository;

    public List<SubscriptionCredit> getAllSubscriptionCredits() {
        return subscriptionCreditRepository.findAll();
    }

    public Optional<SubscriptionCredit> getSubscriptionCreditByAccountId(String accountId) {
        return subscriptionCreditRepository.findSubscriptionCreditByAccountId(accountId);
    }

    public Optional<SubscriptionCredit> createNewSubscriptionCredit(SubscriptionCredit subscriptionCredit) {
        if (subscriptionCreditRepository.findSubscriptionCreditByAccountId(subscriptionCredit.getAccountId()).isEmpty()) {
            subscriptionCreditRepository.save(subscriptionCredit);
        }
        return Optional.of(subscriptionCredit);
    }


}
