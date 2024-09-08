package co.shoping_cart.shoping_cart.Controllers;

import co.shoping_cart.shoping_cart.Dtos.ProductDTO;
import co.shoping_cart.shoping_cart.Models.ApiResponse;
import co.shoping_cart.shoping_cart.Models.Product;
import co.shoping_cart.shoping_cart.ServicesImpl.ProductServiceImpl;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> obtainAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        if (page < 0 || pageSize < 0) {
            System.out.println("Error: page y pageSize deben ser mayores a 0.");
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Error: page y pageSize deben ser mayores a 0.", null, 400));
        }

        List<Product> productos = productService.getAllProducts(page, pageSize);
        return ResponseEntity.ok(new ApiResponse<>("Productos obtenidos exitosamente", productos, 200));
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());

        String result = productService.createNewProduct(product);
        return ResponseEntity.ok(new ApiResponse<>(result, null, 200));
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> obtainProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(new ApiResponse<>("Producto encontrado", product, 200));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>("Producto no encontrado", null, 404));
        }
    }

    // Eliminar producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteProduct(@PathVariable int id) {
        Boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>("Producto eliminado exitosamente", true, 200));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>("Producto no encontrado", false, 404));
        }
    }

    // Obtener productos por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> obtainProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse<>("Productos por categoría obtenidos exitosamente", products, 200));
    }

}
