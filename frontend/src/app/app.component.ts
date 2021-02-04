import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'frakton';
  zmienna: any;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.http.get("http://localhost:8080/api",{responseType: 'text'}).subscribe(data => {
      this.zmienna = data;
      console.log(data);
    });
  }


}
