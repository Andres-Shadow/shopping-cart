import { Product } from "./product";

//Interface for the response of the product service that comes from the backend
//with the list of all products and the rest of the body
export interface ProductResponse {
    message: string;
    data: Product[];
    status: number;
}