package com.dawnmoon.big_event.controller;

import com.dawnmoon.big_event.pojo.Category;
import com.dawnmoon.big_event.pojo.Response;
import com.dawnmoon.big_event.service.CategoryService;
import com.dawnmoon.big_event.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final JWTUtil jwtUtil;

    // 构造器注入
    @Autowired
    public CategoryController(CategoryService categoryService, JWTUtil jwtUtil) {

        this.categoryService = categoryService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public Response<?> add(@RequestBody Category category){
        categoryService.add(category);
        return Response.success("新增文章分类成功");
    }

    @GetMapping
    public Response<List<Category>> list(){

        List<Category> list = categoryService.list();
        return Response.success("获取文章分类列表成功" ,list);
    }

    @GetMapping("detail")
    public Response<?> detail(Integer id){

        Category category = categoryService.detail(id);
        if(!category.getId().equals(jwtUtil.getCurUserId())){
            return Response.error(1, "不允许获取非本用户创建文章分类的详情");
        }
        return Response.success("获取文章分类详情成功", category);
    }

    @PutMapping
    public Response<?> update(@RequestBody @Validated Category category){

        Category processCategory = categoryService.detail(category.getId());
        if(!processCategory.getCreateUser().equals(jwtUtil.getCurUserId())){
            return Response.error(1, "不允许更新非本用户创建文章分类的详情");
        }

        categoryService.update(category);
        return Response.success("更新文章分类详情成功", category.getId());
    }

    @DeleteMapping
    public Response<?> delete(Integer id){
        Category processCategory = categoryService.detail(id);
        if(processCategory == null){
            return Response.error(1, "文章分类不存在");
        }
        if(!processCategory.getCreateUser().equals(jwtUtil.getCurUserId())){
            return Response.error(1, "不允许删除非本用户创建文章分类的详情");
        }

        categoryService.delete(id);
        return Response.success("删除文章分类详情成功", id.toString());
    }
    
    @GetMapping("/count")
    public Response<Integer> count(){
        Integer count = categoryService.count();
        return Response.success("获取文章分类总数成功", count);
    }
}
