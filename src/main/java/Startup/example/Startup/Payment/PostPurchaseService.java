

package Startup.example.Startup.Payment;

import Startup.example.Startup.Payment.Transactions.Transaction;
import Startup.example.Startup.Payment.Transactions.TransactionsService;
import Startup.example.Startup.SubscriptionCredit.SubscriptionCreditService;
import Startup.example.Startup.Subscriptions.Subscription;
import Startup.example.Startup.Subscriptions.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
class PostPurchaseService {

    @Autowired
    SubscriptionCreditService subscriptionCreditService;

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    SubscriptionsService subscriptionsService;

    public boolean provideBenefits(String accountId, int productId) {
        //TODO: get product credit amount (need to create products table)
        int creditAmount = 50;
        if (productId != 1) {
            creditAmount = 100;
        }
        subscriptionCreditService.provideCredit(accountId, creditAmount);
        return true;
    }

    public boolean addPurchaseDetailsToDb(String accountId, String payerId, String orderId, String status, Double price, Integer productId) {

        // Add transaction
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setPayerId(payerId);
        transaction.setOrderId(orderId);
        transaction.setStatus(status);
        transaction.setPrice(price);
        transaction.setProductId(productId);
        LocalDateTime now = LocalDateTime.now();
        transaction.setCreated(now);
        transaction.setLastUpdated(now);

        transactionsService.createNewTransaction(transaction);

        return true;
    }

    public boolean addSubscriptionToDb(String accountId, String billingMode, String orderId, String status, Integer productId) {
        if (productId != 1) { //TODO: fetch from db
            Subscription subscription = new Subscription();
            subscription.setAccountId(accountId);
            subscription.setOrderId(orderId);
            subscription.setBillingMode(billingMode);
            subscription.setStatus(status);
            subscription.setProductId(productId);

            LocalDateTime now = LocalDateTime.now();
            subscription.setCreated(now);
            subscription.setLastUpdated(now);

            // TODO: move to service
            // Calculate next billing date based on billing mode
            LocalDateTime nextBillingDate;
            if ("annual".equalsIgnoreCase(billingMode)) {
                nextBillingDate = now.plus(1, ChronoUnit.YEARS);
            } else if ("monthly".equalsIgnoreCase(billingMode)) {
                nextBillingDate = now.plus(1, ChronoUnit.MONTHS);
            } else {
                nextBillingDate = null;
            }
            subscription.setNextBillingDate(nextBillingDate);

            subscriptionsService.createNewSubscription(subscription);
        }

        return true;
    }



}
