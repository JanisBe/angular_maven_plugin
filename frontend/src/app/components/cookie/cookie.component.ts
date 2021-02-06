import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";

@Component({
  selector: 'app-cookie',
  templateUrl: './cookie.component.html',
  styleUrls: ['./cookie.component.css']
})
export class CookieComponent implements OnInit {
  ipAddress: string;

  constructor(private cookieService: CookieService,
              public snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    if (this.cookieService.check(this._address)) {
      this.ipAddress = this.cookieService.get(this._address);
    }
  }

  doSomething(event) {
    console.log(event) // input value is logged
  }

  private readonly _address = "address";

  saveAddress() {
    this.cookieService.set(this._address, this.ipAddress);
    let config = new MatSnackBarConfig();
    config.verticalPosition = "top";
    config.horizontalPosition = "center";
    this.snackBar.open("Adres serwera dodano", "Zawrzyj", config);
  }
}
