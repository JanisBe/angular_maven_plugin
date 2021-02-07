import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})
export class ClientService {

    constructor(private http: HttpClient,
                public snackBar: MatSnackBar) {
    }


    checkOnline(): Observable<boolean> {
        const response = this.http.get('http://localhost:998/isOnline', {responseType: 'text'});
        return new Observable<boolean>((ret) => {
            return response.subscribe(value => {
                if (value === 'true') {
                    console.log('online');
                    ret.next(true);
                } else if (value === 'offline') {
                    console.log('offline');
                    ret.next(false);
                }
            });
        });
    }

    wakeUp(): Observable<boolean> {
        return new Observable<boolean>((res) => {
            this.getSubscription('http://localhost:998/wakeUp').subscribe((response => {
                console.log('wake up ' + response);
                res.next(response);
            }));
        });
    }

    powerOff(): Observable<boolean> {
        console.log("here");
        return new Observable<boolean>((res) => {
            this.getSubscription('http://localhost:998/shutDown').subscribe((response => {
                console.log('wake up ' + response);
                res.next(response);
            }));
        });
    }


    winampAction(action: string): Observable<string> {
        let headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa('root' + ':' + 'tajne')
        };

        const response = this.http.get('http://localhost:999/winamp/' + action, {
            responseType: 'text',
            headers: headers
        });
        console.log("w serwisie akcja:" + action);
        return new Observable<string>((ret) => {
            return response.subscribe(value => {
                console.log("po akcji:" + value);
                return ret.next(value);
            });
        });
    }


    private getSubscription(url: string): Observable<boolean> {
        return new Observable<boolean>((resp) => {
            this.http.get(url, {responseType: 'text'}).pipe(
                catchError(err => {
                    console.log('Handling error locally and rethrowing it...', err);
                    resp.next(false);
                    return throwError(err);
                })
            ).subscribe(
                res => console.log('HTTP response', res),
                err => {
                    console.log('HTTP Error', err);
                    this.snackBar.open('Nie udało się!!!', 'Zawrzyj');
                    resp.next(false);
                },
                () => {
                    console.log('HTTP request completed.');
                    this.snackBar.open('Akcja zakończona powodzeniam!', 'Zawrzyj');
                    resp.next(true);
                }
            );
        });
    }

    healthCheck(): Observable<boolean> {
        const response = this.http.get('http://localhost:998/healthcheck', {responseType: 'text'});
        return new Observable<boolean>((ret) => {
            return response.subscribe(value => {
                return ret.next(!!value);
            });
        });
    }
}
