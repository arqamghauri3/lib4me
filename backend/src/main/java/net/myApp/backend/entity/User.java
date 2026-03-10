package net.myApp.backend.entity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String username;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id")
    private Library library;
}
