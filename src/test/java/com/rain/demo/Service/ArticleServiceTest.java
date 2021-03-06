package com.rain.demo.Service;

import com.rain.demo.Dao.ArticleMapper;
import com.rain.demo.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleService articleService;

    @Test
    public void getAll(){
        List<Article> allArticle = articleMapper.getAll();
        for(int i=0;i<allArticle.size();i++){
            System.out.println(allArticle.get(i).toString());
        }
    }


    @Test
    public void deleteArt(){
        try {
            List<Article> articlesBefore = articleMapper.getAll();
            System.out.println("Articles Before Deleted:");
            for(int i=0;i<articlesBefore.size();i++){
                System.out.println(articlesBefore.get(i).toString());
            }
            Article article = articleMapper.selectByPrimaryKey(1);
            articleService.deleteArt(1);
            System.out.println("Articles After Deleted:");
            List<Article> articlesAfter = articleMapper.getAll();
            for(int i=0;i<articlesAfter.size();i++){
                System.out.println(articlesAfter.get(i).toString());
            }
            articleMapper.insert(article);
            /*
            Article test = new Article();

            test.setUser_id(1);
            test.setPost_time(new Date());
            test.setCategory_id(1);
            test.setContent("Test without id");
            test.setLikes(1);

            articleMapper.insert(test);
            System.out.println("Insert test withou id:");
            */
            List<Article> testArt = articleService.getAll();
            for(int i=0;i<testArt.size();i++){
                System.out.println(testArt.get(i).toString());
            }
        }catch (Exception e){
            System.out.println("Error");
        }
    }

    @Test
    public void selectByAuthor(){
        List<Article> test = articleService.selectByAuthor("admin");
        for(int i=0;i<test.size();i++){
            System.out.println(test.get(i).toString());
        }
    }

    @Test
    public void selectAll(){
        List<Article> test = articleService.getAll();
        for(int i=0;i<test.size();i++){
            System.out.println(test.get(i).toString());
        }
    }

    @Test
    public void testInsert(){
        Article article = new Article();
        article.setCategory(1);
        article.setTitle("Final test");
        article.setDigit("This is the final test");
        article.setAuthor("admin");
        article.setLikes(0);
        article.setComment_account(0);
        article.setMd_content("# This is the final test");
        article.setContent("This is the final test");
        article.setPost_time(new Date());
        System.out.println(articleService.insert(article));
    }

    @Test
    public void testSearch(){
        List<Article> a = articleService.search("test");
        System.out.println(a.size());
    }

}