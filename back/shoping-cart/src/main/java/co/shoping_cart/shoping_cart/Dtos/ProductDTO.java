package co.shoping_cart.shoping_cart.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    @NotBlank
    private String name;
    
    @NotNull
    @Positive
    private float price;
    
    @NotNull
    private String category;

    @NotNull
    private int amount;
}
