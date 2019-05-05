package com.rain.demo.Service.impl;

import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.Service.ArticleService;
import com.rain.demo.entity.Article;
import com.rain.demo.entity.ArticleHeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAll() {
        return articleMapper.getAll();
    }

    @Override
    public Article selectByPrimaryKey(Integer art_id) {
        return articleMapper.selectByPrimaryKey(art_id);
    }

    @Override
    public int deleteArt(int art_id) {
        return articleMapper.deleteByPrimaryKey(art_id);
    }

    @Override
    public List<Article> selectByAuthor(String author) {
        return articleMapper.selectByAuthor(author);
    }

    @Override
    public int insert(Article record) {
        return articleMapper.insert(record);
    }

    @Override
    public List<Article> getByCategory(Integer category_id) {
        return articleMapper.getByCategory(category_id);
    }

    @Override
    public List<Article> getByAuthor(String author) {
        return articleMapper.selectByAuthor(author);
    }

    @Override
    public int updateByPrimaryKey(Article record) {
        return articleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Article> search(String searchTitle) {
        return articleMapper.search(searchTitle);
    }

    @Override
    public List<ArticleHeat> anaHeat() {
        /*{To Do}*/
        List<Article> material = articleMapper.getHeat();
        List<ArticleHeat> result = new ArrayList<ArticleHeat>();
        for(int i=0;i<material.size();i++){
            result.add(new ArticleHeat(material.get(i).getArticle_id(),material.get(i).getTitle(),material.get(i).getAuthor(),material.get(i).getComment_account()*1+material.get(i).getLikes()*2,material.get(i).getComment_account(),material.get(i).getLikes()));
        }
        Collections.sort(result, new Comparator<ArticleHeat>() {
            @Override
            public int compare(ArticleHeat o1, ArticleHeat o2) {
                return o1.getHeat().compareTo(o2.getHeat());
            }
        });
        List<ArticleHeat> results = new ArrayList<ArticleHeat>();
        for(int i=0;i<5;i++){
            results.add(result.get(i));
        }
        return results;
    }
}
