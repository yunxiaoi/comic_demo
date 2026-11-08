// CarouselService.java
package com.comic.service;

import com.comic.entity.Carousel;
import java.util.List;

public interface CarouselService {
    List<Carousel> getAll();
    List<Carousel> getEnabledCarousels();
    Carousel getById(Integer id);
    void save(Carousel carousel);
    void update(Carousel carousel);
    void deleteById(Integer id);
}