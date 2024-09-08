import { Component, Input, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { ProductResponse } from '../product-response';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
  providers: [ProductService]
})

export class ProductListComponent implements OnInit {

  // Objeto para almacenar el nuevo producto
  newProduct = {
    name: '',
    price: null,
    amount: null,
    category: ''
  };
  selectedCategory = ""
  lProducts: Product[] = [];
  categories = [
    { id: 1, name: 'Electronics' },
    { id: 2, name: 'cat1' },
    { id: 3, name: 'cat2' },
    { id: 4, name: 'Clothing' },
    { id: 5, name: 'Sports' }
  ];

  searchQuery = "";

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe((data: ProductResponse) => {
      this.lProducts = data.data;
    });
  }

  saveProduct(): void {
    this.productService.addNewProduct(this.newProduct).subscribe((data: ProductResponse) => {
      this.loadProducts();
    });
    console.log('Product saved: ', this.newProduct);
    this.loadProducts();
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe((data: ProductResponse) => {
      this.loadProducts();
    });
  }

  searchProductByName(): void {
    if (this.searchQuery == "") {
      this.loadProducts();
      return
    }
    this.productService.obtainProductByName(this.searchQuery).subscribe((res: ProductResponse) => {
      this.lProducts = res.data
    });
  }

  filterByCategory(): void {
    // this.productService.getProductsByCategory(category).subscribe((data: ProductResponse) => {
    //   this.lProducts = data.data;
    // });
    if (this.selectedCategory == "") {
      this.loadProducts();
    } else {
      this.productService.obtainProductsByCategory(this.selectedCategory).subscribe((data: ProductResponse) => {
        this.lProducts = data.data;
      });
    }
  }
}
