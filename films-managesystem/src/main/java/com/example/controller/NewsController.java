package com.example.controller;

import com.example.common.Result;
import com.example.pojo.News;
import com.example.service.NewsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController //标注一下这是一个接口
@RequestMapping("/news")    //一级路径
public class NewsController {

    @Resource
    private NewsService newsService;

    //新增新闻
    @PostMapping("/add")
    public Result add(@RequestBody News news) {
        newsService.add(news);
        return Result.success();
    }

    //删除新闻
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        newsService.deleteById(id);
        return Result.success();
    }

    //修改新闻
    @PutMapping("/update")
    public Result update(@RequestBody News news) {
        newsService.update(news);
        return Result.success();
    }

    //分页查询接口
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "3") Integer pageSize,
                             News news) {
        PageInfo<News> newsPageInfo = newsService.selectPage(pageNum, pageSize, news);
        return Result.success(newsPageInfo);
    }

}
