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
@Tag(name = "Products", description = "Manage products in the store")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Get all products",
            description = "Returns every active product available in the store catalog."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of products"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
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
            description = "Returns one active product that matches the provided product ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Invalid product id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "Product ID to search for", example = "1")
            @PathVariable("id") int productId) {
        return productService.getProductById(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Get products by category",
            description = "Returns active products that belong to the provided category ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "400", description = "Invalid category id"),
            @ApiResponse(responseCode = "404", description = "Category has no products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Category ID used to filter products", example = "1")
            @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .filter(products -> !products.isEmpty())
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Create product",
            description = "Creates a product with a name, category, price, stock quantity, and active status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product saved"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "409", description = "Product already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/save")
    public ResponseEntity<Product> save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data to create. Use an existing categoryId from the catalog.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductRequest.class),
                            examples = @ExampleObject(
                                    name = "Valid product request",
                                    summary = "Product in seeded category 1",
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
            description = "Disables an active product matching the provided product ID. The product is not physically removed from the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid product id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Product ID to disable", example = "1")
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
