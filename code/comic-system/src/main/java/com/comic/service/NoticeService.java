// NoticeService.java
package com.comic.service;

import com.comic.entity.Notice;
import java.util.List;

public interface NoticeService {
    List<Notice> getAll();
    Notice getById(Integer id);
    void save(Notice notice);
    void update(Notice notice);
    void deleteById(Integer id);
}