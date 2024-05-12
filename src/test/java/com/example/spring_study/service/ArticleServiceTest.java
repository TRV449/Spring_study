package com.example.spring_study.service;

import com.example.spring_study.dto.ArticleForm;
import com.example.spring_study.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Test
    void index() {
        //1. 예상 데이터
        Article a = new Article(1L, "rrrr", "rrr");
        Article b = new Article(2L, "asdf", "asdf");
        Article c = new Article(3L, "asdf", "asdf");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        //2. 실제 데이터
        List<Article> ariticels = articleService.index();
        //3. 비교 및 검증
        assertEquals(expected.toString(), ariticels.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        //1. 예상 데이터
        Article expected = new Article(1L, "rrrr", "rrr");
        //2. 실제 데이터
        Article article = articleService.show(1L);
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패_존재하지_않는_id_입력() {
        //1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.show(id);
        //3. 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        //1. 예상 데이터
        String title = "네번째데이터";
        String content = "네번째데이터1";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(10L, title, content);
        //2. 실제 데이터
        Article article = articleService.create(dto);
        //3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        //1. 예상 데이터
        Long id = 11L;
        String title = "네번째데이터";
        String content = "네번째데이터1";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        //2. 실제 데이터
        Article article = articleService.create(dto);
        //3. 비교 및 검증
        assertEquals(expected, article);
    }
}