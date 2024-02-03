import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MesaiService {

  private  // API adresinizi buraya ekleyin

  constructor(private http: HttpClient) { }

  // Mesai verilerini getirme örneği
  getProduct(): Observable<any> {
    const apiUrl = "http://localhost:8081/produtos";

    return this.http.get<any>(apiUrl);
  }

  getCategories(): Observable<any> {
    const apiUrl = "http://localhost:8081/categorias";

    return this.http.get<any>(apiUrl);
  }

  // Diğer HTTP istekleri için gerekli fonksiyonları ekleyebilirsiniz

}
