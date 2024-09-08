import { Component, Input, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { ProductResponse } from '../product-response';
import { FormsModule } from '@angular/forms';
import { CategoryResponse } from '../category-response';
import { Category } from '../category';


@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
  providers: [ProductService]
})

export class ProductListComponent implements OnInit {

  // Object for creating a new product into de list
  newProduct = {
    name: '',
    price: null,
    amount: null,
    category: ''
  };

  // State used to implement the category filter
  selectedCategory = ""

  // List of all the products tha come from the backend
  productList: Product[] = [];

  // List of all the categories that come from the backend
  categories: Category[] = [];

  // State used to implement the search bar an reload the table
  searchQuery = "";

  //State used to implement the edit product popup
  isModalEditVisible: boolean = false;

  // Object created for recieving and udpating the existing info for a product
  editProductData: Product = {
    id: 0,
    name: '',
    price: 0,
    amount: 0,
    category: ''
  };


  //Variables implemented for the pagination part
  //Currentsize shows the number of the actual page
  //Pagesize shows the number of products that are shown in the table
  //Totalpages shows the total number of pages that are show under the table
  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 0;

  //Constructor method for injecting the ProductService dependency into the component
  constructor(private productService: ProductService) { }

  //Method for initializing the component by calling the loadProducts method
  //and preparing the data that will come to the table
  ngOnInit(): void {
    this.loadProducts();
  }

  /*
    Pagination section
  */

  //Method for the pagination service, it allows to go to the next page by incrementing the current page
  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadProducts();
    }
  }

  //Method for the pagination service, it allows to go to the previous page by decrementing the current page
  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadProducts();
    }
  }

  /*
    Product transaction section
  */

  //Method for the edit product popup, it allows to edit the product by cloning the product and showing the popup
  //Parameters: 
  //  product: Product -> The product
  //Return: void
  editProduct(product: Product) {
    this.editProductData = { ...product };
    this.isModalEditVisible = true;
  }

  //Method for the edit product popup, it allows to update the product by updating the product in the list
  //Parameters:
  //  void
  //Return:
  //  void
  updateProduct() {
    // Update the product from the product list
    const index = this.productList.findIndex(p => p.id === this.editProductData.id);

    if (index !== -1) {
      //Calls the service for updating the product
      this.productService.updateProduct(this.editProductData).subscribe((data: ProductResponse) => {
        //Update the product in the list by recalling the loadProducts method
        this.loadProducts();
      });

    }

    this.isModalEditVisible = false;
  }

  //Method for the edit product popup, it allows to close the popup without making any changes
  closeEditPopup() {
    this.isModalEditVisible = false;
  }

  //Main method for this componente it allows to load the products from the backend
  //and the categories for the filter, also it calculates the totalpages size for the pagination section
  //Parameters:
  //  void
  //Return:
  //  void
  loadProducts(): void {
    //Loads the products
    this.productService.getProducts(this.currentPage, this.pageSize).subscribe((data: ProductResponse) => {
      this.productList = data.data;
      const totalItems = parseInt(data.message.split('-')[0].trim());
      this.totalPages = Math.ceil(totalItems / 10);
    });
    //Loads the categories
    this.productService.getCategories().subscribe((data: CategoryResponse) => {
      this.categories = data.data;
    });
  }

  //Method for saving a product into the list, it calls the service for adding a new product and reloads the table
  //the product that is saved was declared as a state so it can be used in the html form
  //Parameters:
  //  void
  //Return:
  //  void
  saveProduct(): void {
    this.productService.addNewProduct(this.newProduct).subscribe((data: ProductResponse) => {
      this.loadProducts();
    });
    console.log('Product saved: ', this.newProduct);
    this.loadProducts();
  }


  //Method for deleting a product from the list, it calls the service for deleting a product and reloads the table
  //Parameters:
  //  id: number -> The id of the product that is going to be deleted
  //Return:
  //  void
  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe((data: ProductResponse) => {
      this.loadProducts();
    });
  }

  /*
    Filters section
   */

  //Method for searching a product by name, it calls the service for searching a product by name
  //and reloads the table with the products that match the search, it uses the searchQuery state
  //Parameters:
  //  void
  //Return:
  //  void
  searchProductByName(): void {
    if (this.searchQuery == "") {
      this.loadProducts();
      return
    }
    this.productService.obtainProductByName(this.searchQuery).subscribe((res: ProductResponse) => {
      this.productList = res.data
    });
  }

  //Method for filtering the products by category, it calls the service for filtering the products by category
  //and reloads the table with the products that match the category, it uses the selectedCategory state
  //Parameters:
  //  void
  //Return:
  //  void
  filterByCategory(): void {
    if (this.selectedCategory == "") {
      this.loadProducts();
    } else {
      this.productService.obtainProductsByCategory(this.selectedCategory).subscribe((data: ProductResponse) => {
        this.productList = data.data;
      });
    }
  }
}
