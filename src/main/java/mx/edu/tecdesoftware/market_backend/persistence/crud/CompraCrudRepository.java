package mx.edu.tecdesoftware.market_backend.persistence.crud;

import mx.edu.tecdesoftware.market_backend.persistence.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompraCrudRepository extends CrudRepository<Compra, Integer> {
    List<Compra> findByIdCliente(String idCliente);
}
