package com.example.spring_study.repository;

import com.example.spring_study.entity.Article;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}
