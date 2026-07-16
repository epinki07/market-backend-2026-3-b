package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.market_backend.domain.service.Purchase;
import mx.edu.tecdesoftware.market_backend.domain.service.PurchaseItem;
import mx.edu.tecdesoftware.market_backend.domain.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchases", description = "Manage purchases in the store")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Operation(
            summary = "Get all purchases",
            description = "Returns every purchase registered in the store."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of purchases"),
            @ApiResponse(responseCode = "404", description = "No purchases found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        List<Purchase> purchases = purchaseService.getAll();
        if (purchases.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @Operation(
            summary = "Get purchases by client ID",
            description = "Returns purchases made by the client matching the provided client ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchases found"),
            @ApiResponse(responseCode = "404", description = "Client has no purchases"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/client/{id}")
    public ResponseEntity<List<Purchase>> getByClient(
            @Parameter(description = "Client ID used to filter purchases", example = "CLI001")
            @PathVariable("id") String clientId) {
        return purchaseService.getByClient(clientId)
                .filter(purchases -> !purchases.isEmpty())
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Create purchase",
            description = "Creates a purchase for an existing client with one or more active products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Purchase saved"),
            @ApiResponse(responseCode = "400", description = "Invalid purchase data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/save")
    public ResponseEntity<Purchase> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Purchase data to create. Use an existing clientId and active product IDs.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid purchase request",
                                    summary = "Purchase for seeded client CLI001",
                                    value = """
                                            {
                                              "clientId": "CLI001",
                                              "date": "2026-07-16T10:30:00",
                                              "paymentMethod": "E",
                                              "comment": "Purchase created from Swagger",
                                              "state": "A",
                                              "items": [
                                                {
                                                  "productId": 1,
                                                  "quantity": 2,
                                                  "total": 36.0,
                                                  "active": true
                                                },
                                                {
                                                  "productId": 3,
                                                  "quantity": 1,
                                                  "total": 28.0,
                                                  "active": true
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
            @RequestBody PurchaseRequest request) {
        Purchase purchase = toPurchase(request);
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }

    private Purchase toPurchase(PurchaseRequest request) {
        Purchase purchase = new Purchase();
        purchase.setClientId(request.getClientId());
        purchase.setDate(request.getDate());
        purchase.setPaymentMethod(request.getPaymentMethod());
        purchase.setComment(request.getComment());
        purchase.setState(request.getState());
        purchase.setItems(toPurchaseItems(request.getItems()));
        return purchase;
    }

    private List<PurchaseItem> toPurchaseItems(List<PurchaseItemRequest> requests) {
        if (requests == null) {
            return null;
        }

        List<PurchaseItem> items = new ArrayList<>(requests.size());
        for (PurchaseItemRequest request : requests) {
            PurchaseItem item = new PurchaseItem();
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
            item.setTotal(request.getTotal());
            item.setActive(request.isActive());
            items.add(item);
        }
        return items;
    }
}
