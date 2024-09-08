package co.shoping_cart.shoping_cart.Logic;

import co.shoping_cart.shoping_cart.Models.Category;
import co.shoping_cart.shoping_cart.Models.CategoryModel;
import co.shoping_cart.shoping_cart.Models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductManager {

    // Constant that contains the path to the json file used as data source
    private static final String JSON_FILE_PATH = "shoping-cart/src/main/resources/products.json";

    // Method that adds a product to the json file
    // Parameters:
    // Product producto-> the product to be added
    // Returns:
    // String-> a message that indicates if the product was added successfully or
    // not
    public static String addProductToJson(Product product) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(JSON_FILE_PATH);
            List<Product> products = file.exists() && file.length() > 0
                    ? mapper.readValue(file,
                            mapper.getTypeFactory().constructCollectionType(ArrayList.class, Product.class))
                    : new ArrayList<>();

            // validate if the product already exists
            boolean exists = products.stream().anyMatch(p -> p.getName().equals(product.getName()));
            if (exists) {
                return "Product already exists.";
            }

            // Get the max id and add 1 to create a new id
            int newId = products.stream().mapToInt(Product::getId).max().orElse(0) + 1;
            product.setId(newId);

            products.add(product);
            mapper.writeValue(file, products);

            return "Product added successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error trying to add the product" + e.getMessage();
        }
    }

    // Method that gets a list of products from the json file
    // Parameters:
    // int page-> the page number to get
    // int pageSize-> the number of products per page
    // Returns:
    // List<Product>-> a list of products
    public static List<Product> getProductsFromJson(int page, int pageSize) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                List<Product> products = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(ArrayList.class, Product.class));

                int fromIndex = (page - 1) * pageSize;
                int toIndex = Math.min(fromIndex + pageSize, products.size());

                // Validate if the page is out of bounds
                if (fromIndex > products.size()) {
                    return new ArrayList<>();
                }

                return products.subList(fromIndex, toIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error trying to get the products from the JSON: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Method that gets the entire list of products from the json file
    // Returns:
    // List<Product>-> a list of products
    public static List<Product> getProductsFromJson() {
        return getProductsFromJson(1, Integer.MAX_VALUE);
    }

    // Method that gets a product by its id
    // Parameters:
    // int id-> the id of the product
    // Returns:
    // Product-> the product with the given id
    public static Product getProductById(int id) {
        return getProductsFromJson().stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Method that gets a product by its name
    // Parameters:
    // String name-> the name of the product
    // Returns:
    // Product-> the product with the given name
    public static Product getProductByName(String name) {
        return getProductsFromJson().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Method that gets a list of products by category
    // Parameters:
    // String category-> the category of the products
    // Returns:
    // List<Product>-> a list of products with the given category
    public static List<Product> getProductsByCategory(String category) {
        return getProductsFromJson().stream()
                .filter(product -> product.getCategory().name().equalsIgnoreCase(category))
                .toList();
    }

    // Method that deletes a product from the json file
    // Parameters:
    // int id-> the id of the product to be deleted
    // Returns:
    // Boolean-> a boolean that indicates if the product was deleted successfully or
    // not
    public static Boolean deleteProduct(int id) {
        List<Product> products = getProductsFromJson();
        boolean removed = products.removeIf(product -> product.getId() == id);

        if (removed) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(new File(JSON_FILE_PATH), products);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while deleting the product: " + e.getMessage());
            }
        }
        return false;
    }

    // Method that updates a product in the json file
    // Parameters:
    // int productId-> the id of the product to be updated
    // Product product-> the new product data
    // Returns:
    // Boolean-> a boolean that indicates if the product was updated successfully or
    // not
    public static Boolean updateProduct(int productId, Product product) {
        List<Product> products = getProductsFromJson();
        int index = products.indexOf(products.stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElse(null));

        if (index != -1) {
            products.set(index, product);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(new File(JSON_FILE_PATH), products);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error while updating a product from the json " + e.getMessage());
            }
        }
        return false;

    }

    // Method that gets all the categories of the products
    // Returns:
    // List<CategoryModel>-> a list of categories
    public static List<CategoryModel> getCategories() {
        // Obtain all the enum values
        Category[] enumCategories = Category.values();

        // Create a list of categories with their respective id
        // using an stream
        List<CategoryModel> categoriesWithId = IntStream.range(0, enumCategories.length)
                .mapToObj(i -> new CategoryModel(i, enumCategories[i]))
                .collect(Collectors.toList());

        return categoriesWithId;
    }
}
