package Startup.example.Startup.SubscriptionCredit;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionCreditRepository extends MongoRepository<SubscriptionCredit, ObjectId> {

    Optional<SubscriptionCredit> findSubscriptionCreditByAccountId(String accountId);

}
