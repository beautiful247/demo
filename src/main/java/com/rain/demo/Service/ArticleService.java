package com.rain.demo.Service;

import com.rain.demo.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    List<Article> getAll();

    int deleteArt(int id);
}
