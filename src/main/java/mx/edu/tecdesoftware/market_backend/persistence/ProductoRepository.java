package mx.edu.tecdesoftware.market_backend.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mx.edu.tecdesoftware.market_backend.domain.repository.ProductRepository;
import mx.edu.tecdesoftware.market_backend.domain.service.Product;
import mx.edu.tecdesoftware.market_backend.persistence.crud.ProductoCrudRepository;
import mx.edu.tecdesoftware.market_backend.persistence.entity.Categoria;
import mx.edu.tecdesoftware.market_backend.persistence.entity.Producto;
import mx.edu.tecdesoftware.market_backend.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper productMapper;
    @PersistenceContext
    private EntityManager entityManager;


    // SELECT * FROM productos
    @Override
    public List<Product> getAll() {
        List<Producto> productos = productoCrudRepository.findByEstadoTrue();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaAndEstadoTrueOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    /*
     SELECT *
     FROM producto
     WHERE cantidad_stock <?
     AND estado = true
      */
    @Override
    public Optional<List<Product>> getScarceProducts(int quantity){
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity,true);
        return productos.map(productMapper::toProducts);
    }

    //Obtener un producto dado el id
    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findByIdProductoAndEstadoTrue(productId)
                .map(producto ->  productMapper.toProduct(producto));
    }

    //Guardar un producto
    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        Producto savedProduct = productoCrudRepository.save(producto);
        if (savedProduct.getIdCategoria() != null) {
            savedProduct.setCategoria(entityManager.find(Categoria.class, savedProduct.getIdCategoria()));
        }
        return productMapper.toProduct(savedProduct);
    }

    //Elimina un producto
    @Override
    public void delete(int productId){
        productoCrudRepository.findById(productId).ifPresent(producto -> {
            producto.setEstado(false);
            productoCrudRepository.save(producto);
        });
    }
}
