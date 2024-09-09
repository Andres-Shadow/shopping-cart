package co.shoping_cart.shoping_cart;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.shoping_cart.shoping_cart.Models.Category;
import co.shoping_cart.shoping_cart.Models.Product;
import co.shoping_cart.shoping_cart.ServicesImpl.ProductServiceImpl;

@SpringBootTest
class ShopingCartApplicationTests {

	@Autowired
	private ProductServiceImpl productService;

	@Test
	void createNewProductTest() {
		// Arrange
		Product newProduct = new Product();
		newProduct.setId(90);
		newProduct.setName("Test");
		newProduct.setPrice(100);
		newProduct.setCategory(Category.CLOTHING);
		// Act
		var result = productService.createNewProduct(newProduct);
		// Assert
		assertNotNull(result);
	}

	@Test
	void updateProductTest() {
		// Arrange
		Product newProduct = new Product();
		newProduct.setId(90);
		newProduct.setName("Test");
		newProduct.setPrice(150);
		newProduct.setCategory(Category.CLOTHING);
		// Act
		var result = productService.updateProduct(newProduct.getId(), newProduct);
		// Assert
		System.out.println(result);
		assertFalse(result);
	}

	@Test
	void deleteProductTest() {
		// Arrange
		int productId = 90;
		// Act
		var result = productService.deleteProduct(productId);
		// Assert
		assertFalse(result);
	}

	@Test
	void getProductByIdTest() {
		// Arrange
		int productId = 90;
		// Act
		var result = productService.getProductById(productId);
		// Assert
		assertNull(result);
	}

	// Constructor
	@Test
	void getAllProductsTest() {
		// Arrange
		int page = 1;
		int pageSize = 10;
		// Act
		var result = productService.getAllProducts(page, pageSize);
		// Assert
		assertNotNull(result);
	}

	@Test
	void getAllProductsByCategoryTest() {
		// Act
		var result = productService.getProductsByCategory("CLOTHING");
		// Assert
		assertNotNull(result);
	}

	@Test
	void getProductsByName() {
		// Act
		var result = productService.getProductByName("Test");
		// Assert
		assertNull(result);
	}

	@Test
	void getAllCategoriesTest() {
		// Act
		var result = productService.getAllCategories();
		// Assert
		assertNotNull(result);
	}

}
