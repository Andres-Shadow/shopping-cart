package co.shoping_cart.shoping_cart.ServicesImpl;

import co.shoping_cart.shoping_cart.Logic.ProductManager;
import co.shoping_cart.shoping_cart.Models.CategoryModel;
import co.shoping_cart.shoping_cart.Models.Product;
import co.shoping_cart.shoping_cart.Services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    // Methods that implement the interface ProductService
    // these methods call the methods from the ProductManager class

    // Method for creating a new product
    // Parameters:
    // Product newProduct-> the product to be created
    // Returns:
    // String-> a message that indicates if the product was created successfully or
    // not
    @Override
    public String createNewProduct(Product newProduct) {
        return ProductManager.addProductToJson(newProduct);
    }

    // Method for deleting a product
    // Parameters:
    // int productId-> the id of the product to be deleted
    // Returns:
    // Boolean-> true if the product was deleted successfully, false otherwise
    @Override
    public Boolean deleteProduct(int productId) {
        return ProductManager.deleteProduct(productId);
    }

    // Method for getting a product by id
    // Parameters:
    // int productId-> the id of the product
    // Returns:
    // Product-> the product with the given id
    @Override
    public Product getProductById(int productId) {
        return ProductManager.getProductById(productId);
    }

    // Method for getting all products
    // Parameters:
    // int page-> the page of the products
    // int pageSize-> the size of the page
    // Returns:
    // List<Product>-> a list of products
    @Override
    public List<Product> getAllProducts(int page, int pageSize) {
        return ProductManager.getProductsFromJson(page, pageSize);
    }

    // Method for getting products by category
    // Parameters:
    // String category-> the category of the products
    // Returns:
    // List<Product>-> a list of products with the given category
    @Override
    public List<Product> getProductsByCategory(String category) {
        return ProductManager.getProductsByCategory(category);
    }

    // Method for updating a product
    // Parameters:
    // int productId-> the id of the product to be updated
    // Product product-> the product with the new information
    // Returns:
    // Boolean-> true if the product was updated successfully, false otherwise
    @Override
    public Boolean updadateProduct(int productId, Product product) {
        return ProductManager.updateProduct(productId, product);
    }

    // Method for getting a product by name
    // Parameters:
    // String name-> the name of the product
    // Returns:
    // Product-> the product with the given name
    @Override
    public Product getProductByName(String name) {
        return ProductManager.getProductByName(name);
    }

    // Method for getting all categories
    // Returns:
    // List<CategoryModel>-> a list of categories
    @Override
    public List<CategoryModel> getAllCategories() {
        return ProductManager.getCategories();
    }

    @Override
    public int getAllProducts() {
        return ProductManager.getProductsFromJson().size();
    }
}
