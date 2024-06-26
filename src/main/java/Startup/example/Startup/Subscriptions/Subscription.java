package Startup.example.Startup.Subscriptions;

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
public class Subscription {

    @Id
    private ObjectId id;
    private String accountId;
    private String orderId;
    private String status;
    private Integer productId;
    private String billingMode;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
}
