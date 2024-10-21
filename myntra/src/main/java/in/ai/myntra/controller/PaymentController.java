package in.ai.myntra.controller;

import in.ai.myntra.model.Payment;
import in.ai.myntra.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public Payment initiatePayment(@RequestBody Payment payment) {
        // Here you might integrate with a payment gateway to initiate the payment
        return paymentService.createPayment(payment);
    }

    @PostMapping("/confirm")
    public Payment confirmPayment(@RequestParam Long paymentId,
                                  @RequestParam String transactionId,
                                  @RequestParam String status) {
        return paymentService.confirmPayment(paymentId, transactionId, status);
    }

    @PostMapping("/cancel")
    public void cancelPayment(@RequestParam Long paymentId) {
        paymentService.cancelPayment(paymentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        try {
            Payment payment = paymentService.getPayment(id);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}