import {Component, OnInit} from '@angular/core';
import {ClientService} from './services/client.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private clientService: ClientService,
              private snackBar: MatSnackBar) {
  }

  isBackendReady = false;

  ngOnInit(): void {
    this.clientService.healthCheck().subscribe(data => {
      this.isBackendReady = data;
      if (!data) {
        this.snackBar.open('Backend service offline', 'Zawrzyj');
      }
    });
  }

}
