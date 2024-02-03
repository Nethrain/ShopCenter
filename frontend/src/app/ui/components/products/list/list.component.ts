import { CustomToastrService, ToastrMessageType, ToastrPosition } from './../../../../services/ui/custom-toastr.service';
import { CreateBasketItem } from './../../../../contracts/basket/create-basket-item';
import { NgxSpinnerService } from 'ngx-spinner';
import { BaseComponent, SpinnerType } from './../../../../base/base.component';
import { BasketService } from './../../../../services/common/models/basket.service';
import { BaseUrl } from './../../../../contracts/products/base-url';
import { FileService } from './../../../../services/common/models/file.service';
import { async } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { ListProduct } from './../../../../contracts/products/list-product';
import { ProductService } from 'src/app/services/common/models/product.service';
import { Component, OnInit } from '@angular/core';
import { SplitInterpolation } from '@angular/compiler';
import { MesaiService } from 'src/app/mesai.service';

interface Product {
  id: number;
  nome: string;
  valor: number;
  imageUrl: string; // Resim URL'si eklenmiştir
}

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {


  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute, private fileService: FileService,
    private basketService: BasketService, spinner: NgxSpinnerService, private customToastrService: CustomToastrService,
    private mesaiService: MesaiService) {

  }


  products: any[] = [];



  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.mesaiService.getProduct().subscribe(
      (data: any[]) => {
        this.products = data.map((product: any) => ({
          id: product.id,
          nome: product.nome,
          valor: product.valor,
          imageUrl: this.getImageUrlByProductName(product.nome) // İlgili fonksiyonu çağırarak resim URL'sini alın
        }));
      },
      error => {
        console.error('Ürünler alınamadı:', error);
      }
    );
  }
  private getImageUrlByProductName(productName: string): string {
    // Her ürün adına göre resim URL'sini belirleyin (istediğiniz resim URL'sini buraya ekleyin)
    switch (productName.toLowerCase()) {
      case 'bilgisayar':
        return 'https://via.placeholder.com/150';
      case 'yazıcı':
        return 'https://example.com/yazici.jpg';
      // Diğer ürünler için de aynı şekilde ekleyebilirsiniz
      default:
        return 'https://example.com/default.jpg'; // Varsayılan bir resim URL'si ekleyebilirsiniz
    }
  }




}
