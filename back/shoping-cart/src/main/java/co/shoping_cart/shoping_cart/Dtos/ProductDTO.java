package co.shoping_cart.shoping_cart.Dtos;

import co.shoping_cart.shoping_cart.Models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// DTO for the product entity, it has a name, a price, a category and an amount
// it is used for the request of the products endpoint, receives the data from
// the user
// and then is mapped to the real entity thats used
public class ProductDTO {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private float price;

    @NotNull
    private Category category;

    @NotNull
    private int amount;
}
