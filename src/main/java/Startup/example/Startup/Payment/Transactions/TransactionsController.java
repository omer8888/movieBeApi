package Startup.example.Startup.Payment.Transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/v1/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/showAll")
    private ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(transactionsService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    private ResponseEntity<Optional<Transaction>> getSingleTransaction(@PathVariable String accountId) {
        return new ResponseEntity<>(transactionsService.getSingleTransaction(accountId), HttpStatus.OK);
    }

    @PostMapping("/create")
    private ResponseEntity<Optional<Transaction>> Create(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionsService.createNewTransaction(transaction), HttpStatus.OK);
    }


}
