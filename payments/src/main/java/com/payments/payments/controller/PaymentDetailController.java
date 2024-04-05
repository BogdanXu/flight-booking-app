package com.payments.payments.controller;

import com.payments.payments.model.PaymentDetail;
import com.payments.payments.service.PaymentDetailService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/paymentDetail")
public class PaymentDetailController {
    public final PaymentDetailService paymentDetailService;


    public PaymentDetailController(PaymentDetailService paymentDetailService) {
        this.paymentDetailService = paymentDetailService;
    }

    @PostMapping
    public Mono<PaymentDetail> createPaymentDetail(@RequestBody PaymentDetail paymentDetail) {
        paymentDetail.setStatus("SUCCESS");

        return paymentDetailService.savePaymentDetail(paymentDetail);
    }
    @GetMapping
    public String test(){
         System.out.println("test");
         return "test1";
    }

//    public void mockStatusPayment(PaymentDetail paymentDetail){
//
//        String bookingId = paymentDetail.getBookingId();
//
//        if (parseInt(bookingId) % 2 ==0 ){
//            paymentDetail.setStatus("SUCCESS");
//        } else {
//            paymentDetail.setStatus("REJECTED");
//        }
//    }

}
