package com.moviecatalog._21223levytskyi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private Integer publishYear;
    private String author;
    private String description;
    private String coverUrl;
    private Double rating;
    private Long genreId;
    private String genreName;
}
