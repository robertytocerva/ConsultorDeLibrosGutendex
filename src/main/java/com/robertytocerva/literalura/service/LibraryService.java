package com.robertytocerva.literalura.service;

import com.robertytocerva.literalura.domain.Author;
import com.robertytocerva.literalura.domain.Book;
import com.robertytocerva.literalura.dto.GutendexAuthorDto;
import com.robertytocerva.literalura.dto.GutendexBookDto;
import com.robertytocerva.literalura.repository.AuthorRepository;
import com.robertytocerva.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final GutendexClient gutendexClient;
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public LibraryService(GutendexClient gutendexClient, BookRepository bookRepo, AuthorRepository authorRepo) {
        this.gutendexClient = gutendexClient;
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    @Transactional
    public void searchAndSaveByTitle(String title) {
        var opt = gutendexClient.searchFirstByTitle(title);
        if (opt.isEmpty()) {
            System.out.println("No se encontró libro en Gutendex para: " + title);
            return;
        }
        GutendexBookDto dto = opt.get();

        // evitar duplicados por gutendexId
        Optional<Book> existing = bookRepo.findByGutendexId(dto.id);
        if (existing.isPresent()) {
            System.out.println("Ya existe en DB: " + existing.get().getTitle() + " [id=" + existing.get().getId() + "]");
            return;
        }

        Set<String> langs = dto.languages == null ? Set.of() :
                dto.languages.stream().filter(Objects::nonNull).map(String::toLowerCase).collect(Collectors.toSet());

        Book book = new Book(dto.id, dto.title, langs, dto.downloadCount);

        // autores
        if (dto.authors != null) {
            for (GutendexAuthorDto a : dto.authors) {
                Author author = authorRepo.findByNameIgnoreCase(a.name)
                        .orElseGet(() -> new Author(a.name, a.birthYear, a.deathYear));
                // actualizar años si llegan nulos en DB y hay datos nuevos
                if (author.getBirthYear() == null && a.birthYear != null) author.setBirthYear(a.birthYear);
                if (author.getDeathYear() == null && a.deathYear != null) author.setDeathYear(a.deathYear);
                authorRepo.save(author);
                book.getAuthors().add(author);
            }
        }

        bookRepo.save(book);
        System.out.println("Guardado: " + book.getTitle() + " (GutendexId=" + book.getGutendexId() + ")");
        System.out.println("Idiomas: " + String.join(",", book.getLanguages()));
        System.out.println("Autores: " + book.getAuthors().stream().map(Author::getName).collect(Collectors.joining("; ")));
    }

    @Transactional(readOnly = true)
    public void printAllBooks() {
        List<Book> books = bookRepo.findAll();
        if (books.isEmpty()) {
            System.out.println("Sin libros registrados.");
            return;
        }
        books.stream()
                .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                .forEach(b -> {
                    String auths = b.getAuthors().stream().map(Author::getName).collect(Collectors.joining("; "));
                    String langs = String.join(",", b.getLanguages());
                    System.out.printf(Locale.ROOT, "- %s | Idiomas: [%s] | Autores: %s | Descargas: %s%n",
                            b.getTitle(), langs, auths,
                            b.getDownloadCount() == null ? "?" : b.getDownloadCount().toString());
                });
    }

    @Transactional(readOnly = true)
    public void printAllAuthors() {
        var authors = authorRepo.findAll();
        if (authors.isEmpty()) {
            System.out.println("Sin autores registrados.");
            return;
        }
        authors.stream()
                .sorted(Comparator.comparing(Author::getName, String.CASE_INSENSITIVE_ORDER))
                .forEach(a -> System.out.printf("- %s (%s - %s) | Libros: %d%n",
                        a.getName(),
                        a.getBirthYear() == null ? "?" : a.getBirthYear().toString(),
                        a.getDeathYear() == null ? "?" : a.getDeathYear().toString(),
                        a.getBooks() == null ? 0 : a.getBooks().size()));
    }

    @Transactional(readOnly = true)
    public void printAuthorsAliveIn(int year) {
        var authors = authorRepo.findAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.println("Sin autores vivos para el año " + year + ".");
            return;
        }
        authors.forEach(a -> System.out.printf("- %s (%s - %s)%n",
                a.getName(),
                a.getBirthYear() == null ? "?" : a.getBirthYear().toString(),
                a.getDeathYear() == null ? "?" : a.getDeathYear().toString()));
    }

    @Transactional(readOnly = true)
    public void printBooksByLanguage(String lang) {
        var books = bookRepo.findByLanguage(lang);
        if (books.isEmpty()) {
            System.out.println("Sin libros para el idioma: " + lang);
            return;
        }
        books.forEach(b -> System.out.printf("- %s | Autores: %s%n",
                b.getTitle(),
                b.getAuthors().stream().map(Author::getName).collect(Collectors.joining("; "))));
    }
}
