import { BaseComponent, SpinnerType } from './../../../base/base.component';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MesaiService } from 'src/app/mesai.service';

interface Product {
  id: number;
  nome: string;
  valor: number;
  imageUrl: string; // Resim URL'si eklenmiştir
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  

  constructor(private mesaiService: MesaiService, private router: Router) {

  }

  navigateToOtherComponent(): void {
    // Yönlendirme işlemi, örneğin "/other-component" URL'ine yapılır
    this.router.navigate(['/products']);
  }


  products: any[] = [];



  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.mesaiService.getCategories().subscribe(
      (data: any[]) => {
        this.products = data.map((product: any) => ({
          id: product.id,
          nome: product.nome,
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
