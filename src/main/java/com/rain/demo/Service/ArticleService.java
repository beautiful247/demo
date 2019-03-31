package com.rain.demo.Service;

import com.rain.demo.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> getAll();

    Article selectByPrimaryKey(Integer art_id);

    int deleteArt(int art_id);

    List<Article> selectByAuthor(String author);

    int insert(Article record);

    List<Article> getByCategory(Integer category_id);

    List<Article> getByAuthor(String author);

    int updateByPrimaryKey(Article record);
}
