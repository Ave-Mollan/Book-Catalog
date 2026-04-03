package com.moviecatalog._21223levytskyi.controller;

import com.moviecatalog._21223levytskyi.dto.BookDto;
import com.moviecatalog._21223levytskyi.repository.GenreRepository;
import com.moviecatalog._21223levytskyi.service.BookService;
import com.moviecatalog._21223levytskyi.service.GoogleBooksParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GoogleBooksParserService googleBooksParserService;
    private final GenreRepository genreRepository;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) Long genreId) {
        List<BookDto> books = (search != null && !search.isBlank())
                ? bookService.search(search)
                : (genreId != null ? bookService.findByGenre(genreId) : bookService.findAll());
        model.addAttribute("books", books);
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("search", search);
        return "books/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("genres", genreRepository.findAll());
        return "books/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("genres", genreRepository.findAll());
        return "books/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BookDto bookDto, Model model) {
        try {
            bookService.save(bookDto);
            return "redirect:/books";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to save book: " + e.getMessage());
            model.addAttribute("book", bookDto);
            model.addAttribute("genres", genreRepository.findAll());
            return "books/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping({"/search-openlibrary", "/search-googlebooks"})
    public String openLibrarySearchForm(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "books/search";
    }

    @PostMapping({"/import-openlibrary", "/import-googlebooks"})
    public String importFromOpenLibrary(@RequestParam String title,
                                        @RequestParam(required = false) Long genreId,
                                        Model model) {
        try {
            BookDto dto = googleBooksParserService.fetchByTitle(title);
            dto.setGenreId(genreId);
            model.addAttribute("book", dto);
            model.addAttribute("genres", genreRepository.findAll());
            return "books/form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("genres", genreRepository.findAll());
            return "books/search";
        }
    }
}
