package Startup.example.Startup.Subscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/transactions")
public class SubscriptionsController {

    @Autowired
    private SubscriptionsService subscriptionsService;

    @GetMapping("/showAll")
    private ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return new ResponseEntity<>(subscriptionsService.getAllSubscriptions(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    private ResponseEntity<Optional<Subscription>> getSingleSubscription(@PathVariable String accountId) {
        return new ResponseEntity<>(subscriptionsService.getSingleSubscription(accountId), HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<Optional<Subscription>> Create(@RequestBody Subscription subscription) {
        return new ResponseEntity<>(subscriptionsService.createNewSubscription(subscription), HttpStatus.OK);
    }


}
