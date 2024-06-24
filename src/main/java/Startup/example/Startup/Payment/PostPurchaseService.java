

package Startup.example.Startup.Payment;

import Startup.example.Startup.Payment.Transactions.Transaction;
import Startup.example.Startup.Payment.Transactions.TransactionsService;
import Startup.example.Startup.SubscriptionCredit.SubscriptionCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class PostPurchaseService
{

    @Autowired
    SubscriptionCreditService subscriptionCreditService;

    @Autowired
    TransactionsService transactionsService;

    public boolean provideBenefits(String accountId, int productId) {
        //TODO: get product credit amount (need to create products table)
        int creditAmount = 50;
        if (productId != 1) {
            creditAmount = 100;
        }
        subscriptionCreditService.provideCredit(accountId, creditAmount);
        return true;
    }

    public boolean addPurchaseDetailsToDb(String accountId, String orderId, Integer price, Integer productId) {

        // Add transaction
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setOrderId(orderId);
        transaction.setPrice(price);
        transaction.setProductId(productId);
        transaction.setCreated("");  // Set appropriate value for created
        transaction.setLastUpdated("");  // Set appropriate value for lastUpdated

        transactionsService.createNewTransaction(transaction);

        return true;
    }


}
