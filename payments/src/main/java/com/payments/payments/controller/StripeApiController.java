package com.payments.payments.controller;

import com.payments.payments.dto.SessionDTO;
import com.payments.payments.dto.StripeChargeDto;
import com.payments.payments.dto.StripeTokenDto;
import com.payments.payments.service.StripeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/v2/stripe")

public class StripeApiController {


    private StripeService stripeService;

    public StripeApiController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/session/payment")
    @ResponseBody
    public SessionDTO sessionPayment(@RequestBody SessionDTO model) {
        return stripeService.createPaymentSession(model);
    }

}