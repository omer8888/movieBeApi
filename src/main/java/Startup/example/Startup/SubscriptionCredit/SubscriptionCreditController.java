package Startup.example.Startup.SubscriptionCredit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/users/credit")
public class SubscriptionCreditController {

    @Autowired
    private SubscriptionCreditService subscriptionCreditService;


    @GetMapping("/showAll")
    private ResponseEntity<List<SubscriptionCredit>> getAllSubscriptionCredits() {
        return new ResponseEntity<>(subscriptionCreditService.getAllSubscriptionCredits(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    private ResponseEntity<Optional<SubscriptionCredit>> getSubscriptionCreditByAccountId(@PathVariable String accountId) {
        return new ResponseEntity<>(subscriptionCreditService.getSubscriptionCreditByAccountId(accountId), HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<Optional<SubscriptionCredit>> Create(@RequestBody SubscriptionCredit subscriptionCredit) {
        return new ResponseEntity<>(subscriptionCreditService.createNewSubscriptionCredit(subscriptionCredit), HttpStatus.OK);
    }

    @GetMapping("/reduce/{accountId}")
    private ResponseEntity<SubscriptionCredit> Reduce(@PathVariable String accountId) {
        return new ResponseEntity<>(subscriptionCreditService.reduceCreditByAccountId(accountId), HttpStatus.OK);
    }

}
