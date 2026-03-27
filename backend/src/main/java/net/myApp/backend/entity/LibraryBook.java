package net.myApp.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "library_book",
        uniqueConstraints = @UniqueConstraint(columnNames = {"library_id","book_id"}))
@Getter
@Setter
public class LibraryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    private ReadingStatus status = ReadingStatus.WANT_TO_READ;

    private Instant startedAt;
    private Instant finishedAt;

    private Integer readPages;
    private String notes;
}