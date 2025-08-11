package com.robertytocerva.literalura.repository;

import com.robertytocerva.literalura.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameIgnoreCase(String name);

    @Query("""
           select a from Author a
           where (:year is null or ( (a.birthYear is null or a.birthYear <= :year)
                                   and (a.deathYear is null or a.deathYear > :year)))
           order by a.name asc
           """)
    List<Author> findAliveInYear(Integer year);
}
