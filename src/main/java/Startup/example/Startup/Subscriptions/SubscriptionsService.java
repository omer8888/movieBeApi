package Startup.example.Startup.Subscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionsService {

    @Autowired
    private SubscriptionsRepository subscriptionsRepository;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionsRepository.findAll();
    }

    public Optional<Subscription> getSingleSubscription(String accountId) {
        return subscriptionsRepository.findSubscriptionByAccountId(accountId);
    }

    public Optional<Subscription> createNewSubscription(Subscription subscription) {
        //on new Transaction saves to db
        subscriptionsRepository.save(subscription);

        return Optional.of(subscription);
    }

}
