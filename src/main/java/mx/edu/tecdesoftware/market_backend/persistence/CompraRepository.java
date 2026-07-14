package mx.edu.tecdesoftware.market_backend.persistence;

import mx.edu.tecdesoftware.market_backend.domain.repository.PurchaseRepository;
import mx.edu.tecdesoftware.market_backend.domain.service.Purchase;
import mx.edu.tecdesoftware.market_backend.persistence.crud.CompraCrudRepository;
import mx.edu.tecdesoftware.market_backend.persistence.crud.ProductoCrudRepository;
import mx.edu.tecdesoftware.market_backend.persistence.entity.Compra;
import mx.edu.tecdesoftware.market_backend.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        List<Compra> compras = (List<Compra>) compraCrudRepository.findAll();
        return purchaseMapper.toPurchases(compras);
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        List<Compra> compras = compraCrudRepository.findByIdCliente(clientId);
        return Optional.of(purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = purchaseMapper.toCompra(purchase);

        // Relaciona cada detalle con la compra principal antes de guardar.
        if (compra.getProductos() != null) {
            compra.getProductos().forEach(producto -> {
                if (producto.getId() == null
                        || producto.getId().getIdProducto() == null
                        || productoCrudRepository.findByIdProductoAndEstadoTrue(producto.getId().getIdProducto()).isEmpty()) {
                    throw new IllegalArgumentException("Purchase contains an inactive or unknown product");
                }
            });
            compra.getProductos().forEach(producto -> producto.setCompra(compra));
        }

        return purchaseMapper.toPurchase(compraCrudRepository.save(compra));
    }
}
