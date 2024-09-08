// src/app/product.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from './product-response';

@Injectable({
  providedIn: 'root'
})

export class ProductService {
  private apiUrl = '/products';

  constructor(private http: HttpClient) { }

  getProducts(): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(this.apiUrl);
  }

  addNewProduct(product: any): Observable<ProductResponse> {
    return this.http.post<ProductResponse>(this.apiUrl, product);
  }

  deleteProduct(id: number): Observable<ProductResponse> {
    return this.http.delete<ProductResponse>(`${this.apiUrl}/${id}`);
  }

  obtainProductsByCategory(category: string): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.apiUrl}/category/${category}`);
  }

  obtainProductByName(name: string): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.apiUrl}/filter/${name}`);
  }
}

