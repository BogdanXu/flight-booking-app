package com.example.notification.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "notifications")
public class Notification {
    @Id
    private String id;

    private String bookingId;
    private String message;
    private Date createdAt;
    private String receiverEmail;

    public Notification(String id, String bookingId, String message, Date createdAt, String receiverEmail) {
        this.id = id;
        this.bookingId = bookingId;
        this.message = message;
        this.createdAt = createdAt;
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Notification() {
    }

    public Notification(String id, String bookingId, String message) {
        this.id = id;
        this.bookingId = bookingId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
