import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { LanguageService } from '../../service/language.service';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})

export class MenuComponent implements OnInit {
    collapsed = true;

    pageId: string;
    username: string;
    isLoggedIn: boolean;

    constructor(private translate: TranslateService, private languageService: LanguageService, private router: Router,
                private authService: AuthService) {
        this.isLoggedIn = this.authService.isAuthenticated();
        this.username = authService.getUserName();
    }

    ngOnInit() {
        this.pageId = this.router.routerState.snapshot.url;
        if (this.router.routerState.snapshot.url.startsWith('/#')) {
            this.pageId = '';
        }
        this.translate.setDefaultLang(this.languageService.getLanguage());

    }

    getLanguage() {
        return this.languageService.getLanguage();
    }

    getUserRole() {
        return this.authService.getUserRole();
    }

    useLanguage(language: string) {
        this.languageService.setLanguage(language);
    }

    logout() {
        this.authService.logout();
    }
}
