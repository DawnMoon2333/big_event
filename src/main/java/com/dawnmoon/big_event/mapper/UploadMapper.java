package com.dawnmoon.big_event.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface UploadMapper {
    @Insert("insert into big_event.upload_files(upload_user, file_url, description, create_time) "+
    "values(#{id}, #{description}, #{url}, #{time})")
    void upload(Integer id, String description, String url, LocalDateTime time);
}
