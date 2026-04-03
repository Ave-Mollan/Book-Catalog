package com.moviecatalog._21223levytskyi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "publish_year")
    private Integer publishYear;

    private String author;

    @Column(length = 10000)
    private String description;

    @Column(name = "cover_url", length = 2048)
    private String coverUrl;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
