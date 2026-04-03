package com.moviecatalog._21223levytskyi.repository;

import com.moviecatalog._21223levytskyi.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
