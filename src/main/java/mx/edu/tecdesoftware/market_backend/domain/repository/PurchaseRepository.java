package mx.edu.tecdesoftware.market_backend.domain.repository;

import mx.edu.tecdesoftware.market_backend.domain.service.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String clientId);
    Purchase save(Purchase purchase);
}
