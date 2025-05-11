package com.dawnmoon.big_event.controller;

import com.dawnmoon.big_event.pojo.Article;
import com.dawnmoon.big_event.pojo.PageBean;
import com.dawnmoon.big_event.pojo.Response;
import com.dawnmoon.big_event.service.ArticleService;
import com.dawnmoon.big_event.service.CategoryService;
import com.dawnmoon.big_event.utils.JWTUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final JWTUtil jwtUtil;
    @Autowired
    public ArticleController(ArticleService articleService, JWTUtil jwtUtil) {

        this.articleService = articleService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Response<?> add(@RequestBody @Validated Article article){

        articleService.add(article);
        return Response.success("新增文章成功");
    }

    @GetMapping
    public Response<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                   @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer state) {

        PageBean<Article> result = articleService.list(pageNum, pageSize, categoryId, state);
        return Response.success("成功获取文章列表", result);
    }

    @GetMapping("/detail")
    public Response<?> detail(@RequestParam Integer id) {

        Article article = articleService.detail(id);
        if (article == null) {
            return Response.error(1, "文章不存在");
        }
        return Response.success("成功获取文章详情", article);

    }

    @PatchMapping
    public Response<?> update(@RequestBody Article article) {

        if (article.getId() == null) {
            return Response.error(1, "文章id不能为空");
        }
        Article TargetArticle = articleService.detail(article.getId());
        if (TargetArticle == null) {
            return Response.error(1, "文章不存在");
        }
        if (!TargetArticle.getCreateUser().equals(jwtUtil.getCurUserId())) {
            return Response.error(1, "只能修改自己的文章");
        }

        articleService.update(article);
        return Response.success("更新文章成功");
    }

    @DeleteMapping
    public Response<?> delete(Integer id) {

        Article TargetArticle = articleService.detail(id);
        if (TargetArticle == null) {
            return Response.error(1, "文章不存在");
        }
        if (!TargetArticle.getCreateUser().equals(jwtUtil.getCurUserId())) {
            return Response.error(1, "只能删除自己的文章");
        }

        articleService.delete(id);
        return Response.success("删除文章成功");

    }

    @GetMapping("/count")
    public Response<Integer> count() {
        Integer count = articleService.count();
        return Response.success("获取文章总数成功", count);
    }
}
