package mx.edu.tecdesoftware.market_backend.persistence.mapper;

import mx.edu.tecdesoftware.market_backend.domain.service.PurchaseItem;
import mx.edu.tecdesoftware.market_backend.persistence.entity.CompraProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseItemMapper {
    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "total", target = "total"),
            @Mapping(source = "estado", target = "active")
    })
    PurchaseItem toPurchaseItem(CompraProducto producto);
    List<PurchaseItem> toPurchaseItems(List<CompraProducto> productos);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "id.idCompra", ignore = true),
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true)
    })
    CompraProducto toCompraProducto(PurchaseItem item);
    List<CompraProducto> toCompraProductos(List<PurchaseItem> items);
}
