package net.myApp.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "library", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<LibraryBook> books = new HashSet<>();

    @OneToOne(mappedBy = "library")
    @JsonIgnore
    private User user;

    // helper methods
    public void addBook(Book book, ReadingStatus status) {
        LibraryBook lb = new LibraryBook();
        lb.setLibrary(this);
        lb.setBook(book);
        lb.setStatus(status);
        books.add(lb);
    }

    public void removeBook(Book book) {
        books.removeIf(lb -> lb.getBook().equals(book));
    }
}