import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ClientService} from '../../services/client.service';
import {MatSlideToggleChange} from '@angular/material/slide-toggle';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmationDialog} from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  @Output()
  toggleChange: EventEmitter<void>;

  constructor(private clientService: ClientService,
              public dialog: MatDialog) {
  }

  isOnline = false;
  isTurnedOn = false;
  isDisabled: boolean;

  ngOnInit(): void {
    this.clientService.checkOnline().subscribe(value => {
      this.isOnline = value;
    });
  }

  confirmWakeUp(): void {
    const dialogRef = this.dialog.open(ConfirmationDialog, {data: 'Czy załączamy kompa?'});
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.clientService.wakeUp().subscribe(value => {
          this.isOnline = value;
          this.isDisabled = value;
          this.isTurnedOn = value;
        });
      } else {
        this.isOnline = false;
        this.isDisabled = false;
      }
    });
  }

  public toggle(event: MatSlideToggleChange): void {
    this.isDisabled = true;
    if (event.checked) {
      this.confirmWakeUp();
      this.isOnline = true;
    } else {
      this.confirmPowerOff();
      this.isOnline = false;
    }
    console.log('toggle', event.checked);

  }

  private confirmPowerOff(): void {
    const dialogRef = this.dialog.open(ConfirmationDialog, {data: 'Czy wyłączyć kompa?'});
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.clientService.powerOff();
      } else {
        this.isOnline = true;
      }
      this.isDisabled = false
    });
  }
}
