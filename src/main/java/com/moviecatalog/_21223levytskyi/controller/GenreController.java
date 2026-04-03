package com.moviecatalog._21223levytskyi.controller;

import com.moviecatalog._21223levytskyi.entity.Genre;
import com.moviecatalog._21223levytskyi.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "genres/list";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
        return "redirect:/genres";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        genreRepository.deleteById(id);
        return "redirect:/genres";
    }
}
