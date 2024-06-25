package Startup.example.Startup.Payment.Transactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    private ObjectId id;
    private String accountId;
    private String payerId;
    private String orderId;
    private String status;
    private Double price;
    private Integer productId;  // Added productId as an integer
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
}
