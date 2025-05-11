package com.dawnmoon.big_event.service;

import com.dawnmoon.big_event.pojo.Article;
import com.dawnmoon.big_event.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, Integer state);

    Article detail(Integer id);
    
    void update(Article article);

    void delete(Integer id);
    
    Integer count();
}
