package com.payments.payments.controller;

import com.payments.payments.dto.PaymentDetailConfirmationDTO;
import com.payments.payments.repository.PaymentDetailRepository;
import com.payments.payments.service.PaymentService;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.BodyInserter;
//import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private KafkaTemplate<String, PaymentDetailConfirmationDTO> kafkaTemplate;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/card/payment/create")
    public Mono<ModelAndView> createCardPayment(
            @RequestParam("sum") @NotNull Integer sum,
            @RequestParam("bookingId") @NotNull String bookingId) {
        System.out.println("Received card payment request with sum = " + sum + " and bookingId = " + bookingId);

        return paymentService.createObjPaymentDetail(sum, bookingId)
                .flatMap(verified -> {

                    if (Boolean.TRUE.equals(verified)) {
                        return Mono.just(new ModelAndView("redirect:/payment/success"));
                    } else {
                        return Mono.just(new ModelAndView("redirect:/payment/error"));
                    }
                })
                .onErrorResume(e -> {
                    System.err.println("Error during payment verification: " + e.getMessage());

                    return Mono.just(new ModelAndView("redirect:/payment/error"));
                });
    }


    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "paymentSuccess";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }
}


