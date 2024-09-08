import { Category } from "./category";

//Interface for the response that comes from the backend
//with the list of all categories
export interface CategoryResponse {
    message: string;
    data: Category[];
    status: number;
}
