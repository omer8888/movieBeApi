package Startup.example.Startup.SubscriptionCredit;

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


    public SubscriptionCredit reduceCreditByAccountId(String accountId) {
        Optional<SubscriptionCredit> subscriptionCreditOptional = subscriptionCreditRepository.findSubscriptionCreditByAccountId(accountId);
        SubscriptionCredit subscriptionCredit = null;
        if (subscriptionCreditOptional.isPresent()) {
            subscriptionCredit = subscriptionCreditOptional.get();

            int currentCredit = subscriptionCredit.getCreditAmount();
            if (currentCredit > 0) {
                subscriptionCredit.setCreditAmount(currentCredit - 1);
                subscriptionCreditRepository.save(subscriptionCredit);
            } else {
                // Optionally handle the case when credit is zero or less
                System.out.println("No credits left to reduce.");
            }
        } else {
            System.out.println("No subscription credit found in SubscriptionCredit table for account ID: " + accountId);
        }
        return subscriptionCredit;
    }



}
