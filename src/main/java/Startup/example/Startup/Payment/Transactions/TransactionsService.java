package Startup.example.Startup.Payment.Transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository TransactionsRepository;

    public List<Transaction> getAllTransactions() {
        return TransactionsRepository.findAll();
    }

    public Optional<Transaction> getSingleTransaction(String accountId) {
        return TransactionsRepository.findTransactionByAccountId(accountId);
    }

    public Optional<Transaction> createNewTransaction(Transaction transaction) {
        //on new Transaction saves to db
        TransactionsRepository.save(transaction);

        return Optional.of(transaction);
    }

}
