// PostCategoryService.java
package com.comic.service;

import com.comic.entity.PostCategory;
import java.util.List;

public interface PostCategoryService {
    List<PostCategory> getAll();
    PostCategory getById(Integer id);
    void save(PostCategory category);
    void update(PostCategory category);
    void deleteById(Integer id);
}