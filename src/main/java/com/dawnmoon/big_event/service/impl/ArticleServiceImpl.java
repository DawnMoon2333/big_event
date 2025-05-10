package com.dawnmoon.big_event.service.impl;

import com.dawnmoon.big_event.mapper.ArticleMapper;
import com.dawnmoon.big_event.pojo.Article;
import com.dawnmoon.big_event.pojo.PageBean;
import com.dawnmoon.big_event.service.ArticleService;
import com.dawnmoon.big_event.utils.JWTUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;
    private final JWTUtil jwtUtil;
    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, JWTUtil jwtUtil) {

        this.articleMapper = articleMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void add(Article article) {
        Integer id = jwtUtil.getCurUserId();
        article.setCreateUser(id);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, Integer state) {

        // 创建封装对象，获取当前用户id
        var result = new PageBean<Article>();
        Integer id = jwtUtil.getCurUserId();

        // 使用 MyBatis 的分页插件 PageHelper 实现分页查询
        // 将传入的 pageNum pageSize 储存在 ThreadLocal 中
        // 调用 startPage后直接执行 Mapper 层的查询，PageHelper 会拦截这个查询，根据 ThreadLocal 中的值和 ArticleMapper.xml 中的动态sql生成进行查询的sql语句
        // Page 类是 ArrayList 的子类，因此可以进行强制类型转换
        PageHelper.startPage(pageNum, pageSize);
        List<Article> helper_result = articleMapper.list(id, categoryId, state);
        Page<Article> page = (Page<Article>) helper_result;

        // 从sql查询结果中获取总数和查询结果，赋值给封装对象
        result.setCount(page.getTotal());
        result.setLists(page.getResult());

        return result;
    }

    @Override
    public Article detail(Integer id) {
        return articleMapper.detail(id);
    }

    @Override
    public void update(Article article) {

        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
