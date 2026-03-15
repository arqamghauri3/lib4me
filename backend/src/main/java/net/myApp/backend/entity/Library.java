package net.myApp.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Instant created_at;

    private Instant modified_at;


    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "library_book",
            joinColumns = @JoinColumn(name = "library_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> bookList;

    @OneToOne(mappedBy = "library")
    @JsonIgnore
    private User user;

}