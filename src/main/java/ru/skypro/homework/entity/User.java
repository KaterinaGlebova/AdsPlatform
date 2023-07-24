package ru.skypro.homework.entity;
import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "path_to_image")
    private String image;

   // @Column (name ="ads")
   // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
   // private List<Ad> ads;

   // @Column (name ="comments")
   // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
   // private List<Comment> comments;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

}
