package com.example.mapper;

import com.example.pojo.News;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface NewsMapper {
    @Select("select * from news where title = #{title}")
    News selectByTitle(String title);

    @Insert("insert into news (title, article, type, publisher, date, url)" +
            "values (#{title}, #{article}, #{type}, #{publisher}, #{date}, #{url})")
    void insert(News news);

    @Delete("delete from news where nid = #{nid}")
    void deleteById(Integer nid);

    @Update("update news set title = #{title}, article = #{article}, type = #{type}, publisher = #{publisher}, date = #{date}, url = #{url} where nid = #{nid}")
    void update(News news);

    @Select("select * from news where title like concat('%',#{title},'%') and article like concat('%',#{article},'%') order by nid desc")
    List<News> selectAll(News news);
}
