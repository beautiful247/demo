package com.rain.demo.entity;

public class ArticleHeat implements Comparable<ArticleHeat> {
    private Integer article_id;
    private String title;

    public ArticleHeat(Integer article_id, String title, String author, Integer heat, Integer comment_count, Integer likes) {
        this.article_id = article_id;
        this.title = title;
        this.author = author;
        this.heat = heat;
        this.comment_count = comment_count;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "ArticleHeat{" +
                "article_id=" + article_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", heat=" + heat +
                ", comment_count=" + comment_count +
                ", likes=" + likes +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String author;
    private Integer heat;
    private Integer comment_count;
    private  Integer likes;

    public Integer getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public ArticleHeat(Integer article_id, String author, Integer heat, Integer comment_count, Integer likes) {
        this.article_id = article_id;
        this.author = author;
        this.heat = heat;
        this.comment_count = comment_count;
        this.likes = likes;
    }


    @Override
    public int compareTo(ArticleHeat o) {
        return this.heat<o.heat?1:0;
    }
}
