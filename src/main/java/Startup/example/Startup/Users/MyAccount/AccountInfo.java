package Startup.example.Startup.Users.MyAccount;

import Startup.example.Startup.Subscriptions.Subscription;
import Startup.example.Startup.Users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//account info is mix of info to show in My account page

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    private User userInfo;
    private Subscription subscriptionInfo;
}
