package com.robertytocerva.literalura.repository;

import com.robertytocerva.literalura.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByGutendexId(Long gutendexId);

    @Query("""
           select b from Book b join b.languages l
           where lower(l) = lower(:lang)
           order by b.title asc
           """)
    List<Book> findByLanguage(String lang);
}
