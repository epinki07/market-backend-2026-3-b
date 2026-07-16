package mx.edu.tecdesoftware.market_backend.persistence.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.market_backend.domain.service.Product;
import mx.edu.tecdesoftware.market_backend.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Products endpoints")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Get all products",
            description = "Get the active products."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(
            summary = "Get product by ID",
            description = "Get one product with its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Wrong product ID"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable("id") int productId) {
        return productService.getProductById(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Get products by category",
            description = "Get products from a category."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "400", description = "Wrong category ID"),
            @ApiResponse(responseCode = "404", description = "Category has no products"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Category ID", example = "1")
            @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .filter(products -> !products.isEmpty())
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Create product",
            description = "Add a product."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product saved"),
            @ApiResponse(responseCode = "400", description = "Wrong product data"),
            @ApiResponse(responseCode = "409", description = "Product exists already"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/save")
    public ResponseEntity<Product> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data. The categoryId must exist.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid product request",
                                    summary = "New product example",
                                    value = """
                                            {
                                              "name": "Pera verde",
                                              "categoryId": 1,
                                              "price": 22.5,
                                              "stock": 40,
                                              "active": true
                                            }
                                            """
                            )
                    )
            )
            @RequestBody ProductRequest request) {
        Product product = toProduct(request);
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete product by ID",
            description = "Set a product as inactive."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "400", description = "Wrong product ID"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Product ID", example = "1")
            @PathVariable("id") int productId) {
        if (productService.delete(productId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Product toProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategoryId(request.getCategoryId());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(request.isActive());
        return product;
    }
}
