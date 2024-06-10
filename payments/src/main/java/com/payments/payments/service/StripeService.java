package com.payments.payments.service;

import com.payments.payments.dto.SessionDTO;
import com.payments.payments.dto.StripeChargeDto;
import com.payments.payments.dto.StripeTokenDto;
import com.payments.payments.model.PaymentDetail;
import com.payments.payments.repository.PaymentDetailRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.Token;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${api.stripe.key}")
    private String stripeApikey;

    private static final Logger log = LoggerFactory.getLogger(StripeService.class);

    private PaymentDetail paymentDetail;


    private final PaymentDetailRepository paymentDetailRepository;

    public StripeService(PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
    }


    @PostConstruct
    private void init(){
        Stripe.apiKey = stripeApikey;
    }

    public StripeTokenDto createCardToken(StripeTokenDto model){

        try{
            Map<String,Object> card  = new HashMap<>();
            card.put("number", model.getCardNumber());
            card.put("exp_month", Integer.parseInt(model.getExpMonth()));
            card.put("exp_year", Integer.parseInt(model.getExpYear()));
            card.put("cvc", model.getCvc());
            Map<String, Object> params = new HashMap<>();
            params.put("card", card);
            Token token = Token.create(params);

            if(token != null && token.getId() != null){
                model.setSuccess(true);
                model.setToken(token.getId());
            }
            return model;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

    }

    public StripeChargeDto charge(StripeChargeDto chargeRequest){
        try{
            chargeRequest.setSuccess(false);
            Map<String,Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", (int) (chargeRequest.getAmount()*100));
            chargeParams.put("currency", "USD");
            chargeParams.put("description", "Payment for id " + chargeRequest.getAdditionalInfo().getOrDefault("ID_TAG",""));
            chargeParams.put("source",chargeRequest.getStripeToken());
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("id", chargeRequest.getChargeId());
            metaData.putAll(chargeRequest.getAdditionalInfo());
            chargeParams.put("metadata", metaData);
            Charge charge = Charge.create(chargeParams);

            if(charge.getPaid()){
                chargeRequest.setChargeId(charge.getId());
                chargeRequest.setSuccess(true);
            }
            return chargeRequest;

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }


    public SessionDTO createPaymentSession(SessionDTO sessionDTO){

      try{
          double amount = paymentDetail.getAmount() ;
          Customer customer = findOrCreateCustomer("test@gmail.com","Test User");
          String clientUrl = "https://localhost:9999";
          SessionCreateParams.Builder sessionCreateParamsBuilder = SessionCreateParams.builder()
                  .setMode(SessionCreateParams.Mode.PAYMENT)
                  .setCustomer(customer.getId())
                  .setSuccessUrl(clientUrl + "/success?session_id={CHECKOUT_SESSION_ID}")
                  .setCancelUrl(clientUrl + "/failure");

          // add item and amount
          sessionCreateParamsBuilder.addLineItem(
                  SessionCreateParams.LineItem.builder()
                          .setQuantity(1L)
                          .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                  .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                          .putMetadata("card_id", "123")
                                          .putMetadata("user_id", sessionDTO.getUserId())
                                          .setName("Testing Stripe")
                                          .build()
                                  )
                                  .setCurrency("USD")
                                  .setUnitAmountDecimal(BigDecimal.valueOf(amount *100))
                                  .build())

                          .build()
          ).build();

          SessionCreateParams.PaymentIntentData paymentIntentData = SessionCreateParams.PaymentIntentData.builder()
                          .putMetadata("card_id", "123")
                                  .putMetadata("user_id", sessionDTO.getUserId())
                                          .build();
          sessionCreateParamsBuilder.setPaymentIntentData(paymentIntentData);
          Session session = Session.create(sessionCreateParamsBuilder.build());
          sessionDTO.setSessionUrl(session.getUrl());
          sessionDTO.setSessionId(session.getId());

          updatePaymentStatus();
      }catch (StripeException e){
          e.printStackTrace();
          log.error("Exception createPaymentSession: ", e);
      }

      return sessionDTO;
    }

    private Customer findOrCreateCustomer(String mail, String fullName) throws StripeException {
        Customer customer;

        CustomerSearchParams params = CustomerSearchParams.builder().setQuery("email:'" +mail+"'").build();

        CustomerSearchResult search = Customer.search(params);
        if(search.getData().isEmpty()){
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder().setName(fullName).setEmail(mail).build();

        customer = Customer.create(customerCreateParams);
        }else {
            customer = search.getData().get(0);
        }
        return customer;
    }

    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public void updatePaymentStatus() {
        if (paymentDetail != null) {
            paymentDetail.setStatus("ACCEPTED");
            paymentDetailRepository.save(paymentDetail).subscribe();
        } else {
            System.out.println("PaymentDetail nu este setat.");
        }
    }

}
