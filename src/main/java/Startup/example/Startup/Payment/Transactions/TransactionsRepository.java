package Startup.example.Startup.Payment.Transactions;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionsRepository extends MongoRepository<Transaction, ObjectId> {

    Optional<Transaction> findTransactionByAccountId(String accountId);

}
