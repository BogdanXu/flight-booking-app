package com.payments.payments.service;

import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PaypalService {

    PaymentDetail paymentDetail;
    private final PaymentDetailRepository paymentDetailRepository;
    private final APIContext apiContext;




    public PaypalService(PaymentDetailRepository paymentDetailRepository, APIContext apiContext) {
        this.paymentDetailRepository = paymentDetailRepository;
        this.apiContext = apiContext;
    }

    public Payment createPayment(Double total, String currency, String method, String intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment() ;
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        updatePaymentStatus();


        return  payment.execute(apiContext, paymentExecution);
    }


    // Metoda pentru actualizarea stării plății
    public void updatePaymentStatus(String paymentId, String newStatus) {
        // Obține detaliile plății din baza de date folosind ID-ul plății
        Optional<PaymentDetail> optionalPaymentDetail = paymentDetailRepository.findById(paymentId).blockOptional();

        // Verifică dacă detaliile plății există în baza de date
        if (optionalPaymentDetail.isPresent()) {
            PaymentDetail paymentDetail = optionalPaymentDetail.get();

            // Actualizează starea plății
            paymentDetail.setStatus(newStatus);

            // Salvează actualizările în baza de date
            paymentDetailRepository.save(paymentDetail);
        } else {
            // Tratează cazul în care detaliile plății nu au fost găsite în baza de date
            // Poți arunca o excepție sau să tratezi acest caz în alt mod, în funcție de nevoile aplicației tale
            throw new RuntimeException("Payment detail with ID " + paymentId + " not found");
        }
    }


    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    // Metoda care va fi apelată pentru a face actualizarea
    public void updatePaymentStatus() {
        if (paymentDetail != null) {
            paymentDetail.setStatus("ACCEPTED");
            paymentDetailRepository.save(paymentDetail).subscribe();
        } else {
            System.out.println("PaymentDetail nu este setat.");
        }
    }


}
