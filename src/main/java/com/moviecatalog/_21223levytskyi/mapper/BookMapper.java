package com.moviecatalog._21223levytskyi.mapper;

import com.moviecatalog._21223levytskyi.dto.BookDto;
import com.moviecatalog._21223levytskyi.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishYear(book.getPublishYear());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setCoverUrl(book.getCoverUrl());
        dto.setRating(book.getRating());
        if (book.getGenre() != null) {
            dto.setGenreId(book.getGenre().getId());
            dto.setGenreName(book.getGenre().getName());
        }
        return dto;
    }

    public Book toEntity(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setPublishYear(dto.getPublishYear());
        book.setAuthor(dto.getAuthor());
        book.setDescription(dto.getDescription());
        book.setCoverUrl(dto.getCoverUrl());
        book.setRating(dto.getRating());
        return book;
    }
}
