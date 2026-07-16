package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product creation request")
public class ProductRequest {
    @Schema(description = "Product name", example = "Pera verde")
    private String name;

    @Schema(description = "Existing category ID", example = "1")
    private int categoryId;

    @Schema(description = "Product sale price", example = "22.5")
    private double price;

    @Schema(description = "Available stock quantity", example = "40")
    private int stock;

    @Schema(description = "Whether the product is active", example = "true")
    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
