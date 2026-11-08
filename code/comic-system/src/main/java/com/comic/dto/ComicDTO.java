package com.comic.dto;

import lombok.Data;

@Data
public class ComicDTO {
    private Integer id;
    private String title;
    private String cover;
    private String author;
    private Integer genreId;
    private Integer regionId;
    private Integer status;
    private Integer isPaid;
    private Double price;
    private String description;
}