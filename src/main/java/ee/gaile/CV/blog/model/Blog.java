package ee.gaile.CV.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @Column(name = "blog_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank(message = "Headline is required")
    @Column(name = "blog_headline", length = 2000)
    private String headline;

    @NotBlank(message = "Article is required")
    @Column(name = "blog_article", length = 10485760)
    private String article;

    @Lob
    @NotNull(message = "Image is required")
    @Column(name = "blog_image")
    private byte[] image;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "blog_date")
    private Date date;

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comments> items = new ArrayList<>();

}
