package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchases found"),
            @ApiResponse(responseCode = "404", description = "No purchases found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        List<Purchase> purchases = purchaseService.getAll();
        if (purchases.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Purchases found"),
            @ApiResponse(responseCode = "404", description = "Client has no purchases")
    })
    @GetMapping("/client/{id}")
    public ResponseEntity<List<Purchase>> getByClient(@PathVariable("id") String clientId) {
        return purchaseService.getByClient(clientId)
                .filter(purchases -> !purchases.isEmpty())
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Purchase saved"),
            @ApiResponse(responseCode = "400", description = "Invalid purchase data")
    })
    @PostMapping("/save")
    public ResponseEntity<Purchase> save(@RequestBody PurchaseRequest request) {
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
