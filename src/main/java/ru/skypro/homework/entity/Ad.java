package ru.skypro.homework.entity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table (name = "ad")

public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private long price;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User user;

    @Column(name = "path_to_image")
    private String image;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "ad")
    private List<Comment> comments;
}
