package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Purchase data")
public class PurchaseRequest {
    @Schema(description = "Client ID", example = "CLI001")
    private String clientId;

    @Schema(description = "Purchase date and time", example = "2026-07-16T10:30:00")
    private LocalDateTime date;

    @Schema(description = "Payment method code", example = "E")
    private String paymentMethod;

    @Schema(description = "Short note", example = "Swagger test purchase")
    private String comment;

    @Schema(description = "Purchase state code", example = "A")
    private String state;

    @Schema(description = "Products in this purchase")
    private List<PurchaseItemRequest> items;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PurchaseItemRequest> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemRequest> items) {
        this.items = items;
    }
}
