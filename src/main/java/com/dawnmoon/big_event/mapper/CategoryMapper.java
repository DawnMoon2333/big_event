package com.dawnmoon.big_event.mapper;

import com.dawnmoon.big_event.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into big_event.category(category_name, category_alias, create_user, create_time) "+
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime})")
    void add(Category category);

    @Select("select * from big_event.category where create_user=#{id}")
    List<Category> list(Integer id);

    @Select("select * from big_event.category where id=#{id}")
    Category detail(Integer id);

    @Update("update big_event.category set category_name=#{categoryName}, category_alias=#{categoryAlias} where id=#{id}")
    void update(Category category);

    @Delete("delete from big_event.category where id=#{id}")
    void delete(Integer id);
}
