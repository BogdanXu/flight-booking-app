package com.example.booking.listener;

import com.example.booking.dto.BookingMessageDTO;
import com.example.booking.dto.NotificationDTO;
import com.example.booking.dto.PaymentDetailConfirmationDTO;
import com.example.booking.dto.PaymentDetailDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.BookingStatus;
import com.example.booking.model.Flight;
import com.example.booking.repository.BookingRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;


@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookingListenerIntegrationTests {

    @Autowired
    private BookingRepository bookingRepository;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

    @Container
    public static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Autowired
    private KafkaTemplate<String, BookingMessageDTO> adminKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, PaymentDetailConfirmationDTO> paymentConfirmationKafkaTemplate;


    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getConnectionString);
    }


    @BeforeAll
    public static void setUp() {
        kafka.start();
        mongoDBContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        kafka.stop();
        mongoDBContainer.stop();
    }


    @Test
    public void givenOkFromPayment_whenBookingIsReserved_thenBookingIsAcceptedByPayment() throws InterruptedException {
        Booking mockBooking = new Booking();
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(mockBooking).subscribe();

        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", true);

        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);


        Thread.sleep(1000);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_PAYMENT)
                .expectComplete()
                .verify();

    }
    @Test
    public void givenOkFromAdmin_whenBookingIsReserved_thenBookingIsAcceptedByAdmin() throws InterruptedException {
        Booking mockBooking = new Booking();
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO bookingMessageDTO = new BookingMessageDTO("123", true);

        adminKafkaTemplate.send("booking-admin-confirmation", bookingMessageDTO);

        Thread.sleep(1000);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.ACCEPTED_BY_ADMIN)
                .expectComplete()
                .verify();
    }

    @Test
    public void givenNotOkFromPayment_whenBookingIsReserved_thenBookingIsRejectedByPayment() throws InterruptedException {
        Booking mockBooking = new Booking();
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(mockBooking).subscribe();

        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", false);

        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);


        Thread.sleep(1000);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.REJECTED_BY_PAYMENT)
                .expectComplete()
                .verify();

    }

    @Test
    public void givenNotOkFromAdmin_whenBookingIsReserved_thenBookingIsRejectedByAdmin() throws InterruptedException {
        Booking mockBooking = new Booking();
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(mockBooking).subscribe();
        BookingMessageDTO bookingMessageDTO = new BookingMessageDTO("123", false);

        adminKafkaTemplate.send("booking-admin-confirmation", bookingMessageDTO);

        Thread.sleep(1000);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.REJECTED_BY_ADMIN)
                .expectComplete()
                .verify();
    }

    @Test
    public void givenOkFromBoth_whenPaymentRespondsFirst_thenBookingIsSuccess() throws InterruptedException {
        Booking mockBooking = new Booking();
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO bookingMessageDTO = new BookingMessageDTO("123", true);
        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", true);

        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);
        Thread.sleep(300);
        adminKafkaTemplate.send("booking-admin-confirmation", bookingMessageDTO);

        Thread.sleep(1000);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.SUCCESS)
                .expectComplete()
                .verify();
    }

    @Test
    public void givenOkFromBoth_whenAdminRespondsFirst_thenBookingIsSuccess() throws InterruptedException {
        Booking mockBooking = new Booking();
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO bookingMessageDTO = new BookingMessageDTO("123", true);
        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", true);

        adminKafkaTemplate.send("booking-admin-confirmation", bookingMessageDTO);
        Thread.sleep(300);
        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);

        Thread.sleep(1000);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.SUCCESS)
                .expectComplete()
                .verify();
    }


    @Test
    public void givenOkFromPaymentFirst_whenAdminRespondsNotOkSecond_thenBookingIsRejected() throws InterruptedException {
        Booking mockBooking = new Booking();
        Flight mockFlight = new Flight();
        mockFlight.setId(1L);
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        mockBooking.setSeats(List.of("1A"));
        mockBooking.setFlight(mockFlight);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO adminDTO = new BookingMessageDTO("123", false);
        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", true);


        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);
        Thread.sleep(500);
        adminKafkaTemplate.send("booking-admin-confirmation", adminDTO);

        Thread.sleep(500);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.REJECTED)
                .expectComplete()
                .verify();
    }

    @Test
    public void givenOkFromAdminFirst_whenPaymentRespondsNotOkSecond_thenBookingIsRejected() throws InterruptedException {
        Booking mockBooking = new Booking();
        Flight mockFlight = new Flight();
        mockFlight.setId(1L);
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        mockBooking.setSeats(List.of("1A"));
        mockBooking.setFlight(mockFlight);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO bookingMessageDTO = new BookingMessageDTO("123", true);
        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", false);

        adminKafkaTemplate.send("booking-admin-confirmation", bookingMessageDTO);
        Thread.sleep(500);
        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);

        Thread.sleep(500);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.REJECTED)
                .expectComplete()
                .verify();
    }

    @Test
    public void givenNotOkFromPaymentFirst_whenAdminRespondsOkSecond_thenBookingIsRejected() throws InterruptedException {
        Booking mockBooking = new Booking();
        Flight mockFlight = new Flight();
        mockFlight.setId(1L);
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        mockBooking.setSeats(List.of("1A"));
        mockBooking.setFlight(mockFlight);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO adminDTO = new BookingMessageDTO("123", true);
        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", false);


        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);
        Thread.sleep(500);
        adminKafkaTemplate.send("booking-admin-confirmation", adminDTO);

        Thread.sleep(500);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.REJECTED)
                .expectComplete()
                .verify();
    }

    @Test
    public void givenNotOkFromAdminFirst_whenPaymentRespondsOkSecond_thenBookingIsRejected() throws InterruptedException {
        Booking mockBooking = new Booking();
        Flight mockFlight = new Flight();
        mockFlight.setId(1L);
        mockBooking.setId("123");
        mockBooking.setBookingStatus(BookingStatus.RESERVED);
        mockBooking.setSeats(List.of("1A"));
        mockBooking.setFlight(mockFlight);
        bookingRepository.save(mockBooking).subscribe();

        BookingMessageDTO adminDTO = new BookingMessageDTO("123", true);
        PaymentDetailConfirmationDTO paymentDTO = new PaymentDetailConfirmationDTO("123", false);


        adminKafkaTemplate.send("booking-admin-confirmation", adminDTO);
        Thread.sleep(500);
        paymentConfirmationKafkaTemplate.send("payment-request-confirmation", paymentDTO);

        Thread.sleep(500);
        Mono<Booking> actual = bookingRepository.findById("123");

        StepVerifier.create(actual)
                .expectNextMatches(booking -> booking.getBookingStatus() == BookingStatus.REJECTED)
                .expectComplete()
                .verify();
    }

}