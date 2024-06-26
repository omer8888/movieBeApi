package Startup.example.Startup.Subscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionsService {

    @Autowired
    private SubscriptionsRepository TransactionsRepository;

    public List<Subscription> getAllSubscriptions() {
        return TransactionsRepository.findAll();
    }

    public Optional<Subscription> getSingleSubscription(String accountId) {
        return TransactionsRepository.findSubscriptionByAccountId(accountId);
    }

    public Optional<Subscription> createNewSubscription(Subscription subscription) {
        //on new Transaction saves to db
        TransactionsRepository.save(subscription);

        return Optional.of(subscription);
    }

}
