package com.example.spring_study.controller;

import com.example.spring_study.dto.ArticleForm;
import com.example.spring_study.entity.Article;
import com.example.spring_study.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        //System.out.println(form.toString());
        log.info(form.toString());

        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info(article.toString());

        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        log.info("id: " + id);
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit/")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1. DTO를 엔테테로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2. 엔티티를 DB에 저장하기
        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터에 갱신하기
        if (target != null) {
            articleRepository.save(articleEntity);
        }
        //3. 수정 결과 페이지로 리다이렉트 하기
        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다.");
        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제되었습니다!");
        }
        //3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
