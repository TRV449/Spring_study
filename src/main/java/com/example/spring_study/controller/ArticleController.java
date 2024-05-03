package com.example.spring_study.controller;

import com.example.spring_study.dto.ArticleForm;
import com.example.spring_study.entity.Article;
import com.example.spring_study.repository.ArticleRepository;import lombok.extern.slf4j.Slf4j;
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
    public String NewArticlesForm(){
        return "articles/new";
    }

    @PostMapping ("/articles/create")
    public String createArticles(ArticleForm form){
        log.info(form.toString());
        //System.out.println(form.toString()); log로 대체

        Article article = form.toEntity(); //DTO를 entity로 변환
        log.info(article.toString());
        //System.out.println(article.toString()); log로 대체

        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString()); log로 대체
        return "redirect:/articles/" + saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id );
        // 1. id를 조회해 데이터 가져오기
        // Optional<Article> articleEntity = articleRepository.findById(id);도 가능하다.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        // id가 없으면 null을 반환한다.
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        // 1 . 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
        // 정확히 하려면 ArrayList<Article>이 맞지만, ArrayList가 List를 포함하기 때문에 가능하다.
        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList",articleEntityList);
        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정하기
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log. info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // 2-1 DB데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 데이터 수정하기, 여기선 클릭으로 수정을 하기 때문에 타겟이 무조거 있지만, 비정상적인 접근을 막기 위해선 if조건문을 꼭 사용해야함
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다! !");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log. info(target. toString());
        // 2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target); // 엔티티를 DB에 저장(갱신)
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}