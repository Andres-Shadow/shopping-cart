package co.shoping_cart.shoping_cart.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Model of the product entity, it has an id, a name, a price, an amount and a
// category
// It is used for the response of the products endpoint
public class Product {
    private int id;
    private String name;
    private double price;
    private int amount;
    private Category category;
}
