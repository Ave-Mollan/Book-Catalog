package com.moviecatalog._21223levytskyi.repository;

import com.moviecatalog._21223levytskyi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenreId(Long genreId);
}
