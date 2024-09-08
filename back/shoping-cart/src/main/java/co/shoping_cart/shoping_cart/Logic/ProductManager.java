package co.shoping_cart.shoping_cart.Logic;

import co.shoping_cart.shoping_cart.Models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    private static final String JSON_FILE_PATH = "shoping-cart/src/main/resources/products.json";

    public static String addProductToJson(Product producto) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            File file = new File(JSON_FILE_PATH);
            List<Product> productos = file.exists() && file.length() > 0
                    ? mapper.readValue(file,
                            mapper.getTypeFactory().constructCollectionType(ArrayList.class, Product.class))
                    : new ArrayList<>();

            // Verificar si el producto ya existe
            boolean exists = productos.stream().anyMatch(p -> p.getName().equals(producto.getName()));
            if (exists) {
                return "El producto ya existe en el archivo JSON.";
            }

            // Generar un nuevo ID basado en el mayor existente
            int newId = productos.stream().mapToInt(Product::getId).max().orElse(0) + 1;
            producto.setId(newId);

            productos.add(producto);
            mapper.writeValue(file, productos);

            return "Producto agregado exitosamente al archivo JSON.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al agregar el producto al archivo JSON: " + e.getMessage();
        }
    }

    public static List<Product> getProductsFromJson(int page, int pageSize) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists() && file.length() > 0) {
                List<Product> productos = mapper.readValue(file,
                        mapper.getTypeFactory().constructCollectionType(ArrayList.class, Product.class));

                // int fromIndex = Math.max((page - 1) * pageSize, 0);
                // int toIndex = Math.min(fromIndex + pageSize, productos.size());

                if (productos.size() < pageSize) {
                    return productos;
                }

                return productos.subList(page, pageSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al obtener los productos del archivo JSON: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static List<Product> getProductsFromJson() {
        return getProductsFromJson(1, Integer.MAX_VALUE);
    }

    public static Product getProductById(int id) {
        return getProductsFromJson().stream()
                .filter(producto -> producto.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Product> getProductsByCategory(String category) {
        return getProductsFromJson().stream()
                .filter(producto -> producto.getCategory().equals(category))
                .toList();
    }

    public static Boolean deleteProduct(int id) {
        List<Product> productos = getProductsFromJson();
        boolean removed = productos.removeIf(producto -> producto.getId() == id);

        if (removed) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(new File(JSON_FILE_PATH), productos);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al eliminar el producto del archivo JSON: " + e.getMessage());
            }
        }
        return false;
    }
}
