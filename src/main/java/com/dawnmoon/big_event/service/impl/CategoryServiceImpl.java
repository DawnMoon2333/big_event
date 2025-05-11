package com.dawnmoon.big_event.service.impl;

import com.dawnmoon.big_event.mapper.CategoryMapper;
import com.dawnmoon.big_event.pojo.Category;
import com.dawnmoon.big_event.service.CategoryService;
import com.dawnmoon.big_event.utils.JWTUtil;
import com.dawnmoon.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final JWTUtil jwtUtil;

    // 构造器注入
    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, JWTUtil jwtUtil) {

        this.categoryMapper = categoryMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void add(Category category) {
        Integer id = jwtUtil.getCurUserId();
        category.setCreateTime(LocalDateTime.now());
        category.setCreateUser(id);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list(jwtUtil.getCurUserId());
    }

    @Override
    public Category detail(Integer id) {
        return categoryMapper.detail(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }

    @Override
    public Integer count() {
        Integer userId = jwtUtil.getCurUserId();
        return categoryMapper.count(userId);
    }
}
