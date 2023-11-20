import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpClientModule,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { CustomeResponse } from '../interface/custom-response';
import { Server } from '../interface/server';
import { Status } from '../enum/status.enum';

@Injectable({
  providedIn: 'root',
})
export class ServerService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // getServers(): Observable<CustomeResponse>{
  //   return this.http.get<CustomeResponse>(`http://localhost:8080/server/list`);
  // }

  servers$ = <Observable<CustomeResponse>>(
    this.http
      .get<CustomeResponse>(`${this.apiUrl}/server/list`)
      .pipe(tap(console.log), catchError(this.handleError))
  );

  save$ = (server: Server) =>
    <Observable<CustomeResponse>>(
      this.http
        .post<CustomeResponse>(`${this.apiUrl}/server/save`, server)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  ping$ = (ipAddress: string) =>
    <Observable<CustomeResponse>>(
      this.http
        .get<CustomeResponse>(`${this.apiUrl}/server/ping/${ipAddress}`)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  filter$ = (status: Status, response: CustomeResponse) =>
    <Observable<CustomeResponse>>new Observable<CustomeResponse>(
      (subscriber) => {
        console.log(response);
        subscriber.next(
          status === Status.ALL
            ? { ...response, message: `Servers filtered by ${status} status` }
            : {
                ...response,
                message:
                  response.data.servers.filter(
                    (server) => server.status === status
                  ).length > 0
                    ? `Servers filtered by 
          ${status === Status.SERVER_UP ? 'SERVER UP' : 'SERVER DOWN'} status`
                    : `No servers of ${status} found`,
                data: {
                  servers: response.data.servers.filter(
                    (server) => server.status === status
                  ),
                },
              }
        );
        subscriber.complete();
      }
    ).pipe(tap(console.log), catchError(this.handleError));

  delete$ = (serverId: number) =>
    <Observable<CustomeResponse>>(
      this.http
        .delete<CustomeResponse>(`${this.apiUrl}/server/delete/${serverId}`)
        .pipe(tap(console.log), catchError(this.handleError))
    );

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(() => `An error occured - Error code: ${error.status}`);
  }
}
