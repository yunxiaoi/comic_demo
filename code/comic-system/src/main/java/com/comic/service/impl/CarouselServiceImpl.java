// CarouselServiceImpl.java
package com.comic.service.impl;

import com.comic.entity.Carousel;
import com.comic.mapper.CarouselMapper;
import com.comic.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> getAll() {
        return carouselMapper.findAll();
    }

    @Override
    public List<Carousel> getEnabledCarousels() {
        return carouselMapper.findEnabled();
    }

    @Override
    public Carousel getById(Integer id) {
        return carouselMapper.findById(id);
    }

    @Override
    public void save(Carousel carousel) {
        carouselMapper.insert(carousel);
    }

    @Override
    public void update(Carousel carousel) {
        carouselMapper.update(carousel);
    }

    @Override
    public void deleteById(Integer id) {
        carouselMapper.deleteById(id);
    }
}