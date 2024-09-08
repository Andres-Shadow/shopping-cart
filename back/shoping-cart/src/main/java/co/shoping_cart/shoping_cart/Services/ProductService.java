package co.shoping_cart.shoping_cart.Services;

import co.shoping_cart.shoping_cart.Models.Product;

import java.util.List;

public interface ProductService {

    public String createNewProduct(Product newProduct);

    public Boolean deleteProduct(int productId);

    public Product getProductById(int productId);

    public List<Product> getAllProducts(int page, int pageSize);

    public List<Product> getProductsByCategory(String category);
}
