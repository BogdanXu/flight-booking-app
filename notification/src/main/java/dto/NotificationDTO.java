package dto;

public class NotificationDTO {
    private String id;

    private String bookingId;
    private String message;

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
}
