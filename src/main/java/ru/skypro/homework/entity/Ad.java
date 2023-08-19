package ru.skypro.homework.entity;
import lombok.*;
import javax.persistence.*;
import java.util.List;
/**
 * Класс объявления, данные хранятся в базе данных
 */

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
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Integer price;

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
