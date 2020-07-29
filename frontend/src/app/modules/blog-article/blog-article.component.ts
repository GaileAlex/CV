import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Blog } from '../../models/blog';
import { BlogService } from '../../service/blog.service';
import { AuthService } from '../../service/auth/auth.service';

@Component({
    selector: 'app-blog-article',
    templateUrl: './blog-article.component.html',
    styleUrls: ['./blog-article.component.css']
})
export class BlogArticleComponent implements OnInit {
    isLoggedIn: boolean;
    blog: Blog;

    constructor(private router: ActivatedRoute, private blogService: BlogService, private authService: AuthService) {
    }

    ngOnInit() {
        window.scrollTo(0, 0);
        this.isLoggedIn = this.authService.isAuthenticated();
        const id = this.router.snapshot.params.id;
        this.blogService.findBlogById(id).subscribe(data => {
            this.blog = data;
        });
    }


}
