package co.shoping_cart.shoping_cart.ServicesImpl;

import co.shoping_cart.shoping_cart.Logic.ProductManager;
import co.shoping_cart.shoping_cart.Models.Product;
import co.shoping_cart.shoping_cart.Services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    //Función que recibe un objeto producto
    //y lo agrega a la lista json que se encuentra en el archivo products.json
    // en el caso de que el proceso sea exitoso retorna true
    // caso contrario retorna false
    @Override
    public String createNewProduct(Product newProduct) {
        return ProductManager.addProductToJson(newProduct);
    }

    @Override
    public Boolean deleteProduct(int productId) {
        return ProductManager.deleteProduct(productId);
    }

    @Override
    public Product getProductById(int productId) {
        return ProductManager.getProductById(productId);
    }

    @Override
    public List<Product> getAllProducts(int page, int pageSize) {
        return ProductManager.getProductsFromJson(page, pageSize);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return ProductManager.getProductsByCategory(category);
    }
}
