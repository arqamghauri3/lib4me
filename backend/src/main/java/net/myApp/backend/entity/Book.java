package net.myApp.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String key;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private String image;

    @Column(length = 2000)
    private String description;

    private Integer pages;

    private Double ratings;

    private Integer releaseDate;

    private String publisher;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genreList;


}
