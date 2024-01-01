package com.example.service;

import com.example.exception.CustomException;
import com.example.mapper.NewsMapper;
import com.example.pojo.News;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
public class NewsService {
    @Resource
    private NewsMapper newsMapper;


    //增加新闻
    public void add(News news) {
        News dbNews = newsMapper.selectByTitle(news.getTitle());
        if (dbNews != null) {
            //已经存在
            throw new CustomException("该新闻已存在");
        }
        if (ObjectUtils.isEmpty(news.getTitle())) {
            news.setTitle(news.getTitle());
        }
        newsMapper.insert(news);
    }

    //删除新闻
    public void deleteById(Integer id) {
        newsMapper.deleteById(id);
    }

    //修改新闻
    public void update(News news) {
        newsMapper.update(news);
    }

    //分页
    public PageInfo<News> selectPage(Integer pageNum, Integer pageSize, News news) {
        //数据分页
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper
        List<News> newslist = newsMapper.selectAll(news);
        return PageInfo.of(newslist);
    }
}
