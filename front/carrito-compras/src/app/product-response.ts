import { Product } from "./product";

export interface ProductResponse {
    message: string;
    data: Product[];
    status: number;
}