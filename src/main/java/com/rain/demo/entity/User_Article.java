package com.rain.demo.entity;

import java.util.Date;

public class User_Article {
    private Integer user_id;

    private String name;

    private Integer article_id;

    private String title;

    private Integer category_id;

    private Date post_time;

    private Integer likes;

    private Integer comment_account;

    private String content;

    public User_Article(Integer user_id, String name, Integer article_id, String title, Integer category_id, Date post_time, Integer likes, Integer comment_account, String content) {
        this.user_id = user_id;
        this.name = name;
        this.article_id = article_id;
        this.title = title;
        this.category_id = category_id;
        this.post_time = post_time;
        this.likes = likes;
        this.comment_account = comment_account;
        this.content = content;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Date getPost_time() {
        return post_time;
    }

    public void setPost_time(Date post_time) {
        this.post_time = post_time;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComment_account() {
        return comment_account;
    }

    public void setComment_account(Integer comment_account) {
        this.comment_account = comment_account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
