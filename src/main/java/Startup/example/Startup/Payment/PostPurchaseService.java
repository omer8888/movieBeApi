

package Startup.example.Startup.Payment;

import Startup.example.Startup.SubscriptionCredit.SubscriptionCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PostPurchaseService
{

    @Autowired
    SubscriptionCreditService subscriptionCreditService;

    public boolean provideBenefits(String accountId, int productId){
        //TODO: get product credit amount (need to create products table)
        int creditAmount = 50;
        subscriptionCreditService.provideCredit(accountId, creditAmount);
        return true;
    }

}
