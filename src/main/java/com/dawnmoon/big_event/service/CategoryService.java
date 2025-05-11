package com.dawnmoon.big_event.service;

import com.dawnmoon.big_event.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category detail(Integer id);

    void update(Category category);

    void delete(Integer id);

    Integer count();
}
