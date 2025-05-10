package com.dawnmoon.big_event.mapper;

import com.dawnmoon.big_event.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into big_event.article(category_id, create_user, title, content, cover_pic_url, state, create_time, update_time) "+
    "values(#{categoryId}, #{createUser}, #{title}, #{content}, #{coverPicUrl}, #{state}, #{createTime}, #{updateTime})")
    void add(Article article);

    // 使用动态sql
    List<Article> list(Integer id, Integer categoryId, Integer state);

    @Select("select * from big_event.article where id=#{id}")
    Article detail(Integer id);

    // 使用动态sql
    void update(Article article);

    @Delete("delete from big_event.article where id=#{id}")
    void delete(Integer id);
}
