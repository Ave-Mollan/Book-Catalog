package com.moviecatalog._21223levytskyi.service;

import com.moviecatalog._21223levytskyi.dto.BookDto;
import com.moviecatalog._21223levytskyi.entity.Book;
import com.moviecatalog._21223levytskyi.entity.Genre;
import com.moviecatalog._21223levytskyi.mapper.BookMapper;
import com.moviecatalog._21223levytskyi.repository.BookRepository;
import com.moviecatalog._21223levytskyi.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
    }

    public List<BookDto> search(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(bookMapper::toDto).toList();
    }

    public List<BookDto> findByGenre(Long genreId) {
        return bookRepository.findByGenreId(genreId).stream().map(bookMapper::toDto).toList();
    }

    public BookDto save(BookDto dto) {
        Book book = bookMapper.toEntity(dto);
        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(() -> new RuntimeException("Genre not found"));
            book.setGenre(genre);
        }
        return bookMapper.toDto(bookRepository.save(book));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
