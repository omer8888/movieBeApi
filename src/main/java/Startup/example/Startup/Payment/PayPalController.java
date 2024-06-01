package Startup.example.Startup.Payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class PayPalController {

    @PostMapping("/paypal-transaction-complete")
    public ResponseEntity<?> completeTransaction(@RequestBody Map<String, String> data) {
        String orderID = data.get("orderID");
        String payerID = data.get("payerID");

        // Capture the payment
        try {
            // Assuming you have a method to handle payment capture
            boolean isPaymentCaptured = capturePayment(orderID, payerID);
            if (isPaymentCaptured) {
                // Handle successful payment
                // TODO: add credit to the user, and redirect to generate
                // TODO: send TY email ?
                return ResponseEntity.ok("Payment captured successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment capture failed.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment details.");
    }

    private boolean capturePayment(String orderID, String payerID) {
        // Implement your payment capture logic here
        return true; // Return true if payment is captured successfully
    }
}
