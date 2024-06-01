package Startup.example.Startup.Payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
public class PayPalWebhookController {

    @PostMapping("/paypal")
    public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> event) {
        String eventType = (String) event.get("event_type");

        if ("PAYMENT.CAPTURE.COMPLETED".equals(eventType)) {
            Map<String, Object> resource = (Map<String, Object>) event.get("resource");
            String orderID = (String) resource.get("id");

            // Update user credits based on orderID or other relevant information
            System.out.println("Payment capture completed for Order ID: " + orderID);

            return ResponseEntity.ok("Webhook received.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event not handled.");
    }
}
