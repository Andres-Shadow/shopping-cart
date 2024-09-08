// src/app/product.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from './product-response';
import { CategoryResponse } from './category-response';

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  //proxy cnfiguration
  private apiUrl = '/products';

  //Constructor for the product service thar implements the http client
  constructor(private http: HttpClient) { }

  //Method for getting the products from the backend with the pagination
  //Parameters:
  //page: number -> the actual page that is shown
  //pageSize: number -> the number of products that are shown in the table
  //Returns:
  //Observable<ProductResponse> -> the list of all products that are shown in the table
  getProducts(page: number, pageSize: number): Observable<ProductResponse> {
    let url = `${this.apiUrl}?page=${page}&pageSize=${pageSize}`;
    return this.http.get<ProductResponse>(url);
  }

  //Method for getting the categories from the backend
  //Parameters:
  //Returns:
  //Observable<CategoryResponse> -> the list of all categories that are shown in the table
  getCategories(): Observable<CategoryResponse> {
    return this.http.get<CategoryResponse>(this.apiUrl + '/category');
  }

  //Method for adding a new product into the list
  //Parameters:
  //product: any -> the new product that is going to be added
  //Returns:
  //Observable<ProductResponse> -> the list of all products that are shown in the table
  addNewProduct(product: any): Observable<ProductResponse> {
    return this.http.post<ProductResponse>(this.apiUrl, product);
  }

  //Method for deleting a product from the list
  //Parameters:
  //id: number -> the id of the product that is going to be deleted
  //Returns:
  //Observable<ProductResponse> -> the list of all products that are shown in the table
  deleteProduct(id: number): Observable<ProductResponse> {
    return this.http.delete<ProductResponse>(`${this.apiUrl}/${id}`);
  }

  //Method for getting the products by category
  //Parameters:
  //category: string -> the category that is going to be filtered
  //Returns:
  //Observable<ProductResponse> -> the list of all products that are shown in the table
  obtainProductsByCategory(category: string): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.apiUrl}/category/${category}`);
  }

  //Method for updating a product from the list
  //Parameters:
  //product: any -> the product that is going to be updated
  //Returns:
  //Observable<ProductResponse> -> the list of all products that are shown in the table
  updateProduct(product: any): Observable<ProductResponse> {
    return this.http.put<ProductResponse>(`${this.apiUrl}/${product.id}`, product);
  }

  //Method for getting the products by name
  //Parameters:
  //name: string -> the name of the product that is going to be filtered
  //Returns:
  //Observable<ProductResponse> -> the list of all products that are shown in the table
  obtainProductByName(name: string): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.apiUrl}/filter/${name}`);
  }
}

