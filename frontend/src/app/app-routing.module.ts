import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StartPageComponent } from './modules/start-page/start-page.component';
import { NotFoundComponent } from './modules/not-found/not-found.component';
import { MindlyComponent } from './modules/mindly/mindly.component';
import { LibrarianComponent } from './modules/librarian/librarian.component';
import { BooksComponent } from './modules/books/books.component';
import { BlogComponent } from './modules/blog/blog.component';
import { LoginComponent } from './modules/login/login.component';
import { RegistrationComponent } from './modules/registration/registration.component';
import { ProtectedGuardService } from './service/protectedGuard';
import { BlogAdminComponent } from './modules/blog-admin/blog-admin.component';
import { BlogArticleComponent } from "./modules/blog-article/blog-article.component";


export const routes: Routes = [
    {path: '', component: StartPageComponent},
    {path: 'mindly', component: MindlyComponent},
    {path: 'librarian', component: LibrarianComponent},
    {path: 'blog', component: BlogComponent},
    {path: 'article/:id', component: BlogArticleComponent},
    {path: 'books/:param', component: BooksComponent},
    {path: 'login', component: LoginComponent},
    {path: 'registration', component: RegistrationComponent},
    {path: 'blog-admin', component: BlogAdminComponent, canActivate: [ProtectedGuardService]},
    {path: '**', component: NotFoundComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
