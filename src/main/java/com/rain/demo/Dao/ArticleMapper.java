package com.rain.demo.Dao;

import com.rain.demo.entity.Article;
import com.rain.demo.entity.ArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int countByExample(ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int deleteByExample(ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int deleteByPrimaryKey(Integer article_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int insert(Article record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int insertSelective(Article record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    List<Article> selectByExampleWithBLOBs(ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    List<Article> selectByExample(ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    Article selectByPrimaryKey(Integer article_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int updateByPrimaryKeySelective(Article record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(Article record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table articles
     *
     * @mbggenerated Mon Feb 04 13:48:49 CST 2019
     */
    int updateByPrimaryKey(Article record);

    List<Article> getAll();
}