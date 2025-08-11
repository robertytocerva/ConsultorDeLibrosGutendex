package com.robertytocerva.literalura.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "books", indexes = {
        @Index(name = "idx_books_gutendex_id", columnList = "gutendexId", unique = true)
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id numérico de Gutendex
    private Long gutendexId;

    @Column(nullable = false)
    private String title;

    // múltiples idiomas por libro
    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "lang_code", length = 10)
    private Set<String> languages = new HashSet<>();

    private Integer downloadCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"book_id","author_id"}))
    private Set<Author> authors = new HashSet<>();

    public Book() {}

    public Book(Long gutendexId, String title, Set<String> languages, Integer downloadCount) {
        this.gutendexId = gutendexId;
        this.title = title;
        if (languages != null) this.languages = languages;
        this.downloadCount = downloadCount;
    }

    // getters/setters
    public Long getId() { return id; }

    public Long getGutendexId() { return gutendexId; }
    public void setGutendexId(Long gutendexId) { this.gutendexId = gutendexId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Set<String> getLanguages() { return languages; }
    public void setLanguages(Set<String> languages) { this.languages = languages; }

    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    public Set<Author> getAuthors() { return authors; }
    public void setAuthors(Set<Author> authors) { this.authors = authors; }
}
