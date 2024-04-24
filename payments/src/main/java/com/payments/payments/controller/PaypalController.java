package com.payments.payments.controller;

import com.payments.payments.dto.PaymentDetailDTO;
import com.payments.payments.model.Account;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import com.payments.payments.service.AccountService;
import com.payments.payments.service.PaymentDetailService;
import com.payments.payments.service.PaymentService;
import com.payments.payments.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/paypal")
public class PaypalController {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaypalService paypalService;
    private final AccountService  accountService;
    private final PaymentDetailService paymentDetailService;
    private final PaymentDetailRepository paymentDetailRepository;

//    private final PaymentDetail paymentDetail;

//    public PaypalController(PaypalService paypalService, AccountService accountService, PaymentDetailService paymentDetailService, PaymentDetailRepository paymentDetailRepository, PaymentDetail paymentDetail) {
//        this.paypalService = paypalService;
//        this.accountService = accountService;
//        this.paymentDetailService = paymentDetailService;
//        this.paymentDetailRepository = paymentDetailRepository;
//        this.paymentDetail = paymentDetail;
//    }

    public PaypalController(PaypalService paypalService, AccountService accountService, PaymentDetailService paymentDetailService, PaymentDetailRepository paymentDetailRepository) {
        this.paypalService = paypalService;
        this.accountService = accountService;
        this.paymentDetailService = paymentDetailService;
        this.paymentDetailRepository = paymentDetailRepository;
    }


//    public PaypalController(PaypalService paypalService, AccountService accountService, PaymentDetailService paymentDetailService) {
//        this.paypalService = paypalService;
//        this.accountService = accountService;
//        this.paymentDetailService = paymentDetailService;
//    }



//    public PaypalController(PaypalService paypalService) {
//        this.paypalService = paypalService;
//    }

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @PostMapping(value = "/payment/create")
    public ResponseEntity<Object> createPayment() {
        try {

            log.info("createPayment-LOG");
            String cancelUrl = "http://localhost:9999/paypal/payment/cancel";
            String successUrl = "http://localhost:9999/paypal/payment/success";

            Payment payment = paypalService.createPayment(11.0, "EUR", "paypal", "sale",
                    "operatorIban", cancelUrl, successUrl);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(new URI(links.getHref()));
                    return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
                }
            }
        } catch (PayPalRESTException |  URISyntaxException e) {
            log.error("ERROR occurred: ", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment creation.");
    }

//    @PostMapping(value = "/payment/create")
//    public ResponseEntity<Object> createPayment(@RequestParam double amount) {
//        try {
//            log.info("createPayment-LOG");
//            String cancelUrl = "http://localhost:9999/paypal/payment/cancel";
//            String successUrl = "http://localhost:9999/paypal/payment/success";
//
//            Payment payment = paypalService.createPayment(amount, "EUR", "paypal", "sale",
//                    "Payment description", cancelUrl, successUrl);
//
//            for (Links links : payment.getLinks()) {
//                if (links.getRel().equals("approval_url")) {
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.setLocation(new URI(links.getHref()));
//                    return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
//                }
//            }
//        } catch (PayPalRESTException |  URISyntaxException e) {
//            log.error("ERROR occurred: ", e);
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment creation.");
//    }



//    @PostMapping(value ="/payment/create")
//    public RedirectView createPayment(){
//        try{
//            log.info("createPayment-LOG");
//            String cancelUrl = "https://localhost:9999/paypal/payment/cancel";
//            String successUrl = "https://localhost:9999/paypal/payment/success";
//
//            Payment payment = paypalService.createPayment(10.0, "USD", "paypal","sale",
//                    "Payment description", cancelUrl, successUrl);
//
//            for(Links links: payment.getLinks()){
//                if(links.getRel().equals("approval_url")){
//                    return new RedirectView(links.getHref());
//                }
//            }
//        }catch (PayPalRESTException e){
//            log.error("ERROR occurred: ", e);
//        }
//        return  new RedirectView("payment/error");
//
//    }


//    @GetMapping("/payment/success")
//    public String paymentSuccess(@RequestParam("paymentId") String paymentId,
//        @RequestParam("PayerID") String payerId){
//        try{
//            log.info("paymentSuccess-LOG");
//            Payment payment = paypalService.executePayment(paymentId, payerId);
//            if(payment.getState().equals("approved")) {
//                return "paymentSuccess";
//            }
//        }catch (PayPalRESTException e){
//            log.error("ERROR occurred: ", e);
//        }
//            return "paymentSuccess";
//
//    }

//    @GetMapping("/payment/success")
//    public String paymentSuccess(@RequestParam("paymentId") String paymentId,
//                                 @RequestParam("PayerID") String payerId,
//                                 @RequestParam("token") String token) {
//        log.info("Handling payment success callback: Payment ID = {}, Payer ID = {}, Token = {}", paymentId, payerId, token);
//        try {
//            Payment payment = paypalService.executePayment(paymentId, payerId);
//            if ("approved".equalsIgnoreCase(payment.getState())) {
//
//                return "paymentSuccess";
//            } else {
//                log.warn("Payment not approved: State = {}", payment.getState());
//                return "paymentError";  // Consider redirecting to a different page or template if not approved
//            }
//        } catch (PayPalRESTException e) {
//            log.error("ERROR occurred: ", e);
//            return "paymentError";  // Redirect to an error handling page
//        }
//    }


//@GetMapping("/payment/success")
//public Mono<String> paymentSuccess(@RequestParam("paymentId") String paymentId,
//                                   @RequestParam("PayerID") String payerId,
//                                   @RequestParam("token") String token) {
//    log.info("Handling payment success callback: Payment ID = {}, Payer ID = {}, Token = {}", paymentId, payerId, token);
//
//    return Mono.fromCallable(() -> paypalService.executePayment(paymentId, payerId)) // Wrap the synchronous method call
//            .flatMap(payment -> {
//                if ("approved".equalsIgnoreCase(payment.getState())) {
//                    // Processing logic when payment is approved
//                    // For example, update the operator's account balance and save the payment details.
//                    // The actual implementation details would depend on your application logic.
//
//
//                    return Mono.just("paymentSuccess");
//                } else {
//                    log.warn("Payment not approved: State = {}", payment.getState());
//                    return Mono.just("paymentError"); // Consider redirecting to a different page or template if not approved
//                }
//            })
//            .onErrorResume(PayPalRESTException.class, e -> {
//                log.error("ERROR occurred: ", e);
//                return Mono.just("paymentError"); // Handle PayPalRESTException and return an error page
//            });
//}

//    @GetMapping("/payment/success")
//    public Mono<String> paymentSuccess(@RequestParam("paymentId") String paymentId,
//                                       @RequestParam("PayerID") String payerId,
//                                       @RequestParam("token") String token) {
//        log.info("Handling payment success callback: Payment ID = {}, Payer ID = {}, Token = {}", paymentId, payerId, token);
//
//        // Define the operator's IBAN and amount to update
//        String operatorIban = "operatorIban"; // This should be retrieved from config or as part of payment details
//        Double paymentAmount = 7.0; // The payment amount to be credited to the operator
//
//        return Mono.fromCallable(() -> paypalService.executePayment(paymentId, payerId))
//                .flatMap(payment -> {
//                    if ("approved".equalsIgnoreCase(payment.getState())) {
//                        // Retrieve and update the operator's account balance
//                        return accountService.getAccount(operatorIban)
//                                .flatMap(operatorAccount -> {
//                                    operatorAccount.setBalance(operatorAccount.getBalance() + paymentAmount);
//                                    return accountService.updateAccount(operatorAccount);
//                                })
//                                .then(Mono.just("paymentSuccess")); // Return success response
//                    } else {
//                        log.warn("Payment not approved: State = {}", payment.getState());
//                        return Mono.just("paymentError");
//                    }
//                })
//                .onErrorResume(PayPalRESTException.class, e -> {
//                    log.error("ERROR occurred: ", e);
//                    return Mono.just("paymentError");
//                });
//    }

    @GetMapping("/payment/success")
    public Mono<String> paymentSuccess(@RequestParam("paymentId") String paymentId,
                                       @RequestParam("PayerID") String payerId,
                                       @RequestParam("token") String token) {
        log.info("Handling payment success callback: Payment ID = {}, Payer ID = {}, Token = {}", paymentId, payerId, token);
//        String operatorIban = accountService.getAccount("operatorIban");

        return Mono.fromCallable(() -> paypalService.executePayment(paymentId, payerId))
                .flatMap(payment -> {
                    if ("approved".equalsIgnoreCase(payment.getState())) {
                        // Retrieve and update the operator's account balance
                        return accountService.getAccount(payment.getTransactions().get(0).getDescription())
                                .flatMap(operatorAccount -> {
                                    double amount = Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal());
                                    operatorAccount.setBalance((operatorAccount.getBalance() + amount));
                                    return accountService.updateAccount(operatorAccount);
                                })
                                .thenReturn("paymentSuccess"); // Return success response
                    } else {
                        log.warn("Payment not approved: State = {}", payment.getState());
                        return Mono.just("paymentError");
                    }
                })
                .onErrorResume(PayPalRESTException.class, e -> {
                    log.error("ERROR occurred: ", e);
                    return Mono.just("paymentError");
                });
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError(){
        return "paymentError";
    }

}
