package Startup.example.Startup.SubscriptionCredit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "subscription_credit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionCredit {

    @Id
    private ObjectId id;
    private String accountId;
    private int subscriptionId;
    private int productId;
    private int creditAmount;
    private Date lastUpdated;

}