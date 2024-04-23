package com.example.notification.dto;

import java.util.Date;

public class NotificationDTO {
    private String id;

    private String bookingId;
    private String message;

    private Date createdAt;

    public NotificationDTO() {
    }

    public NotificationDTO(String id, String bookingId, String message) {
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
