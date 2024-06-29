package Startup.example.Startup.Users.MyAccount;

import Startup.example.Startup.Subscriptions.Subscription;
import Startup.example.Startup.Subscriptions.SubscriptionsService;
import Startup.example.Startup.Users.User;
import Startup.example.Startup.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/MyAccount")
public class MyAccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionsService subscriptionsService;

    @GetMapping("/{accountId}")
    private ResponseEntity<AccountInfo> getAccountInfo(@PathVariable String accountId) {
        Optional<Subscription> subscriptionInfo = subscriptionsService.getLatestSubscription(accountId);
        Optional<User> userInfo = userService.getSingleUser(accountId);

        if (subscriptionInfo.isEmpty() || userInfo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUserInfo(userInfo.get());
        accountInfo.setSubscriptionInfo(subscriptionInfo.get());

        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }


}
