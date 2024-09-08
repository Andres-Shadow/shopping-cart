package co.shoping_cart.shoping_cart.Services;

import co.shoping_cart.shoping_cart.Models.CategoryModel;
import co.shoping_cart.shoping_cart.Models.Product;

import java.util.List;

//interface for product service
//it contains all the methods that the service must implement
public interface ProductService {

    // method for creating a new product
    public String createNewProduct(Product newProduct);

    // method for deleting a product
    public Boolean deleteProduct(int productId);

    // method for getting a product by id
    public Product getProductById(int productId);

    // method for getting a product by name
    public Product getProductByName(String name);

    // method for getting all products
    public List<Product> getAllProducts(int page, int pageSize);

    // method for getting all products without pagination
    public int getAllProducts();

    // method for getting products by category
    public List<Product> getProductsByCategory(String category);

    // method for getting products by price
    public List<CategoryModel> getAllCategories();

    // method for updating a product
    public Boolean updadateProduct(int productId, Product product);
}
