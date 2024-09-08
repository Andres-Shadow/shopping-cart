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

  isEditVisible: boolean = false;
  editProductData: Product = { id: 0, name: '', price: 0, amount: 0, category: '' };

  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 2; // Esto lo puedes actualizar según la respuesta del backend.

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadProducts();
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadProducts();
    }
  }

  editProduct(product: Product) {
    this.editProductData = { ...product }; // Clona el producto para editar
    this.isEditVisible = true; // Muestra el popup
  }

  updateProduct() {
    // Actualiza el producto en la lista de productos
    const index = this.lProducts.findIndex(p => p.id === this.editProductData.id);

    if (index !== -1) {
      // this.lProducts[index] = this.editProductData;
      this.productService.updateProduct(this.editProductData).subscribe((data: ProductResponse) => {
        this.loadProducts();
      });
    }
    this.isEditVisible = false; // Oculta el popup
  }

  closeEditPopup() {
    this.isEditVisible = false; // Cierra el popup sin hacer cambios
  }

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts(this.currentPage, this.pageSize).subscribe((data: ProductResponse) => {
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
    if (this.selectedCategory == "") {
      this.loadProducts();
    } else {
      this.productService.obtainProductsByCategory(this.selectedCategory).subscribe((data: ProductResponse) => {
        this.lProducts = data.data;
      });
    }
  }
}
