import {Component, OnInit} from '@angular/core';
import {ClientService} from '../../services/client.service';

@Component({
    selector: 'app-winamp',
    templateUrl: './winamp.component.html',
    styleUrls: ['./winamp.component.css']
})
export class WinampComponent implements OnInit {
    currentlyPlaying: string;

    constructor(private clientService: ClientService) {
    }

    ngOnInit(): void {
        this.clientService.winampAction('TITLE').subscribe((value => {
            this.currentlyPlaying = value
        }));
    }

    fireAction($event: MouseEvent): void {
        const action = ($event.currentTarget as HTMLButtonElement).getAttribute('data-action');
        this.clientService.winampAction(action).subscribe();
    }
}
