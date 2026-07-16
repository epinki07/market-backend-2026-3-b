package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product item included in a purchase")
public class PurchaseItemRequest {
    @Schema(description = "Existing active product ID", example = "1")
    private int productId;

    @Schema(description = "Quantity purchased", example = "2")
    private int quantity;

    @Schema(description = "Total price for this item", example = "36.0")
    private double total;

    @Schema(description = "Whether the purchase item is active", example = "true")
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
