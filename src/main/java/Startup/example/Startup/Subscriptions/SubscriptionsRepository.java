package Startup.example.Startup.Subscriptions;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionsRepository extends MongoRepository<Subscription, ObjectId> {

    Optional<Subscription> findSubscriptionByAccountId(String accountId);

}
