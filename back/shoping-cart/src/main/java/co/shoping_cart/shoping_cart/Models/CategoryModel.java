package co.shoping_cart.shoping_cart.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Model for the category of the product
// its used for the response of the categories endpoint
public class CategoryModel {
    private int id;
    private Category name;

}