package com.example.pojo;

import lombok.Data;

@Data
public class News {
    private Integer nid;

    private String title;

    private String article;

    private String type;

    private String publisher;

    private String date;

    private String url;
}
