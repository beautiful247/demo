package com.rain.demo.entity;

import java.util.Date;

public class Comment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.comment_id
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    private Integer comment_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.article_id
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    private Integer article_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.user_name
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    private String user_name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.comm_time
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    private Date comm_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.content
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.comment_id
     *
     * @return the value of comment.comment_id
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public Integer getComment_id() {
        return comment_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.comment_id
     *
     * @param comment_id the value for comment.comment_id
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.article_id
     *
     * @return the value of comment.article_id
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public Integer getArticle_id() {
        return article_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.article_id
     *
     * @param article_id the value for comment.article_id
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.user_name
     *
     * @return the value of comment.user_name
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.user_name
     *
     * @param user_name the value for comment.user_name
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.comm_time
     *
     * @return the value of comment.comm_time
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public Date getComm_time() {
        return comm_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.comm_time
     *
     * @param comm_time the value for comment.comm_time
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public void setComm_time(Date comm_time) {
        this.comm_time = comm_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.content
     *
     * @return the value of comment.content
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.content
     *
     * @param content the value for comment.content
     *
     * @mbggenerated Sun Mar 31 18:04:45 CST 2019
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}