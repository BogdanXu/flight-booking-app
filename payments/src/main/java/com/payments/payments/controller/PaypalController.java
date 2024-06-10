package com.payments.payments.controller;

import com.payments.payments.dto.PaymentDetailDTO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/paypal")
public class PaypalController {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

//    PaymentDetail paymentDetail ;

    private final PaypalService paypalService;
    private final AccountService  accountService;
    private final PaymentDetailService paymentDetailService;
    private final PaymentDetailRepository paymentDetailRepository;

//    public PaypalController(PaymentDetail paymentDetail, PaypalService paypalService, AccountService accountService,
//                            PaymentDetailService paymentDetailService, PaymentDetailRepository paymentDetailRepository) {
//        this.paymentDetail = paymentDetail;
//        this.paypalService = paypalService;
//        this.accountService = accountService;
//        this.paymentDetailService = paymentDetailService;
//        this.paymentDetailRepository = paymentDetailRepository;
//    }


        public PaypalController(PaypalService paypalService, AccountService accountService, PaymentDetailService paymentDetailService, PaymentDetailRepository paymentDetailRepository) {
        this.paypalService = paypalService;
        this.accountService = accountService;
        this.paymentDetailService = paymentDetailService;
        this.paymentDetailRepository = paymentDetailRepository;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(value = "sum",  required = false, defaultValue = "0") Integer sum,
            @RequestParam(value= "bookingId",required = false, defaultValue = "abc123") String bookingId,
            Model model
    ) {
        model.addAttribute("sum", sum);
        model.addAttribute("bookingId", bookingId);
        return "index";
    }

    @PostMapping(value = "/payment/create")
    public ResponseEntity<?> createPayment(@RequestParam(value = "sum") Integer sum, @RequestParam(value = "bookingId") String bookingId) {
        try {
            Double amount = Double.valueOf(sum);

            log.info("createPayment-LOG");
            String cancelUrl = "http://localhost:9999/paypal/payment/cancel";
            String successUrl = "http://localhost:9999/paypal/payment/success";

            Payment payment = paypalService.createPayment(amount, "EUR", "paypal", "sale",
                    "operatorIban", cancelUrl, successUrl);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    // Construim răspunsul JSON
                    Map<String, String> response = new HashMap<>();
                    response.put("redirectUrl", links.getHref());

                    // Setăm Content-Type header la application/json
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);

                    // Returnăm răspunsul ca JSON
                    return new ResponseEntity<>(response, headers, HttpStatus.OK);
                }
            }
        } catch (PayPalRESTException e) {
            log.error("ERROR occurred: ", e);
            // Trimiteți un răspuns cu eroarea specifică pentru a putea fi gestionată de client
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        // În cazul în care nu s-a găsit URL-ul de aprobare
        Map<String, String> response = new HashMap<>();
        response.put("error", "Could not find approval URL.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @GetMapping("/payment/success")
    public Mono<String> paymentSuccess(@RequestParam("paymentId") String paymentId,
                                       @RequestParam("PayerID") String payerId,
                                       @RequestParam("token") String token) {
        log.info("Handling payment success callback: Payment ID = {}, Payer ID = {}, Token = {}", paymentId, payerId, token);

        return Mono.fromCallable(() -> paypalService.executePayment(paymentId, payerId))
                .flatMap(payment -> {
                    if ("approved".equalsIgnoreCase(payment.getState())) {
//                        String bookingId = paymentRepository.findBookingIdById(paymentId);
//                        paypalService.updatePaymentStatus2(bookingId);



                        // Retrieve and update the operator's account balance


                        return accountService.getAccount(payment.getTransactions().get(0).getDescription())
                                .flatMap(operatorAccount -> {
                                    double amount = Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal());
                                    operatorAccount.setBalance((operatorAccount.getBalance() + amount));
//                                    paymentDetail.setStatus("ACCEPTED");
//                                    paymentDetailRepository.save(paymentDetail).subscribe();
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
