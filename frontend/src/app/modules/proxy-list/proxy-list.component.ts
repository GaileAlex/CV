import { Component } from '@angular/core';
import { ProxyService } from "../../service/proxy.service";
import { ProxyLists } from "../../models/proxyLists";

@Component({
    selector: 'app-proxy-list',
    templateUrl: './proxy-list.component.html',
    styleUrls: ['./proxy-list.component.css']
})
export class ProxyListComponent {
    proxyList: ProxyLists[];
    total: number;
    page = 0;
    pageSize = 20;

    constructor(private proxyService: ProxyService) {

        this.proxyService.findAll(this.pageSize, this.page).subscribe(data => {
            this.proxyList = data.proxyLists;
            this.total = data.total;
        }, error => {
            this.proxyList = error;
        });

    }

    changePage(event) {
        this.proxyService.findAll(this.pageSize, event.pageIndex).subscribe(data => {
            this.proxyList = data.proxyLists;
            this.total = data.total;
        }, error => {
            this.proxyList = error;
        });
    }
}
