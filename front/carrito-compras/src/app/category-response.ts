import { Category } from "./category";

export interface CategoryResponse {
    message: string;
    data: Category[];
    status: number;
}
