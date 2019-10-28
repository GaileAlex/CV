import { Component } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {LanguageService} from "./service/language.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})



export class AppComponent {
  title = 'frontend';

  constructor(private languageService: LanguageService){
    languageService.setDefault();

  }

}
