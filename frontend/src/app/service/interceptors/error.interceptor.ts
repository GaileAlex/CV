import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';
import { UserDataService } from '../user-data.service';
import { Router } from "@angular/router";
import { ConfirmationService } from "primeng/api";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private auth: AuthService, private userDataService: UserDataService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if ([401, 403].includes(err.status) && this.userDataService.getUserName()) {
                // auto logout if 401 or 403 response returned from api
                this.auth.logout();
            }
            let error = (err && err.error && err.error.message) || err.statusText;
            if ([502].includes(err.status)) {
                error = 'Server not responding';
            } else {
                error = 'Server not responding'
            }
            return throwError(error);
        }));
    }
}
