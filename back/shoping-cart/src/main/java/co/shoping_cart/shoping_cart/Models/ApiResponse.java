package co.shoping_cart.shoping_cart.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Class with the model for the backend response, it has a message, a data and a
// status
// data is a generic type so it can be any type of object that the server could
// return
public class ApiResponse<T> {
    // Message of the response
    private String message;
    // Data of the response
    private T data;
    // Status of the response
    private int status;

}