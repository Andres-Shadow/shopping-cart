package co.shoping_cart.shoping_cart.Controllers;

import co.shoping_cart.shoping_cart.Dtos.ProductDTO;
import co.shoping_cart.shoping_cart.Models.ApiResponse;
import co.shoping_cart.shoping_cart.Models.CategoryModel;
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

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    // Service that implements the ProductService interface
    // and contains the methods for the products
    private final ProductServiceImpl productService;

    // Constructor for the ProductController class that allows to instantiate serive
    // implementations
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    // Method that gets all the products
    // Parameters:
    // int page-> the page number to get
    // int pageSize-> the number of products per page
    // Returns:
    // ResponseEntity<ApiResponse<List<Product>>>-> a response entity with the list
    // of products
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> obtainAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        if (page < 0 || pageSize < 0) {
            System.out.println("Error: page and pageSize must be greater than 0.");
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("Error: page and pageSize must be greater than 0.", null, 400));
        }

        List<Product> products = productService.getAllProducts(page, pageSize);
        int productsOriginalAmount = productService.getAllProductsSize();
        return ResponseEntity
                .ok(new ApiResponse<>(productsOriginalAmount + "- Records listed correctly", products, 200));
    }

    // Method that creates a new product
    // Parameters:
    // ProductDTO productDTO-> the product to be created
    // Returns:
    // ResponseEntity<ApiResponse<String>>-> a response entity with a message
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            // Validar campos obligatorios
            if (productDTO.getName() == null || productDTO.getPrice() < 0 ||
                    productDTO.getCategory() == null || productDTO.getAmount() < 0) {

                return ResponseEntity.status(400)
                        .body(new ApiResponse<>(
                                "Error: All fields (Name, Price, Category, Amount) are required and must be valid",
                                null, 400));
            }

            // Crear nuevo producto
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setCategory(productDTO.getCategory());
            product.setAmount(productDTO.getAmount());

            // Llamada al servicio para guardar el producto
            String result = productService.createNewProduct(product);
            return ResponseEntity.ok(new ApiResponse<>(result, null, 200));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>("Error while creating the product: " + e.getMessage(), null, 500));
        }
    }

    // Method that gets a product by its id
    // Parameters:
    // int id-> the id of the product
    // Returns:
    // ResponseEntity<ApiResponse<Product>>-> a response entity with the product
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> obtainProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(new ApiResponse<>("Product founded", product, 200));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>("Product not founded", null, 404));
        }
    }

    // Method that gets a product by its name
    // Parameters:
    // String name-> the name of the product
    // Returns:
    // ResponseEntity<ApiResponse<List<Product>>>-> a response entity with the
    // product
    @GetMapping("/filter/{name}")
    public ResponseEntity<ApiResponse<List<Product>>> obtainProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        List<Product> products = new ArrayList<>();
        products.add(product);
        if (product != null) {
            return ResponseEntity.ok(new ApiResponse<>("Product founded", products, 200));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>("Product not founded", null, 404));
        }
    }

    // Method that updates a product
    // Parameters:
    // int id-> the id of the product
    // ProductDTO productDTO-> the product with the new information
    // Returns:
    // ResponseEntity<ApiResponse<String>>-> a response entity with a message
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(@PathVariable int id,
            @RequestBody ProductDTO productDTO) {
        try {
            // Validate obligatory fields
            if (productDTO.getName() == null || productDTO.getPrice() < 0 ||
                    productDTO.getCategory() == null || productDTO.getAmount() < 0) {

                return ResponseEntity.status(400)
                        .body(new ApiResponse<>(
                                "Error: All fields (Name, Price, Category, Amount) are required and must be valid",
                                null, 400));
            }

            // Create the new product with the arrived data
            Product product = new Product();
            product.setId(id);
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setAmount(productDTO.getAmount());
            product.setCategory(productDTO.getCategory());

            // Try to update the product
            Boolean updated = productService.updateProduct(product.getId(), product);
            if (updated) {
                return ResponseEntity.ok(new ApiResponse<>("Product updated sucsessfully", null, 200));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>("Product not found", null, 404));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>("Error updating the product: " + e.getMessage(), null, 500));
        }
    }

    // Method that deletes a product
    // Parameters:
    // int id-> the id of the product
    // Returns:
    // ResponseEntity<ApiResponse<Boolean>>-> a response entity with a boolean
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteProduct(@PathVariable int id) {
        Boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>("Product deleted sucessfully", true, 200));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>("Product not founded", false, 404));
        }
    }

    // Method that gets the products by category
    // Parameters:
    // String category-> the category of the products
    // Returns:
    // ResponseEntity<ApiResponse<List<Product>>>-> a response entity with the list
    // of products
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Product>>> obtainProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse<>("Products listed by its category sucessfully", products, 200));
    }

    // Method that gets all the categories
    // Returns:
    // ResponseEntity<ApiResponse<List<CategoryModel>>>-> a response entity with the
    // list of categories
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<CategoryModel>>> getMethodName() {
        List<CategoryModel> categories = productService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>("Categories listed", categories, 200));
    }

}
