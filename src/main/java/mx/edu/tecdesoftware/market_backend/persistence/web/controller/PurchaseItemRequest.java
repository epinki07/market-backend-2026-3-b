package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product in a purchase")
public class PurchaseItemRequest {
    @Schema(description = "Product ID", example = "1")
    private int productId;

    @Schema(description = "Amount to buy", example = "2")
    private int quantity;

    @Schema(description = "Total price", example = "36.0")
    private double total;

    @Schema(description = "Active item", example = "true")
    private boolean active;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
