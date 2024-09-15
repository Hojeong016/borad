package com.example.borad.controller;

import com.example.borad.dto.ArticleForm;
import com.example.borad.entity.Article;
import com.example.borad.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j //로깅 기능을 사용할 수 있는 어노테이션
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm) {
        //dto를 엔티티로 변환
        Article article = articleForm.toEntity();
        Article save = articleRepository.save(article);
        log.info(save.toString());
        //특정 유저의 게시물 가져오기

        return "redirect:/articles/"+save.getId();
    }

    //나중에 내가 쓴 게시물만 불어오기? 적용할 수 있겠다..
    @GetMapping("/articles/{id}")
    public String show(@PathVariable long id, Model model){
        log.info("id ={}", id); // 받아 온 id 찍기
        //id를 조회해 데이터 가져오기
        Article article = articleRepository.findById(id).orElse(null);
        //or - Optional<Article> article = articleRepository.findById(id)
        //모델에 데이터 등록하기
        model.addAttribute("article", article);
        //뷰페이지 반환하기
        return "articles/show";
    }

    @GetMapping("/article")
    public String index(Model model){
        //DB 에서 모든 정보를 가져오기
        List<Article> articleList = articleRepository.findAll();
        //가져온 article들이 담긴 리스트을 모델에 등록하기
        model.addAttribute("articleList", articleList);
        //사용자에게 보여 줄 뷰 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable long id, Model model){
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    private String update(ArticleForm articleForm) {
        //디티오에 수정한 내용이 담긴 폼 내용을 담은 엔티티 생성
        Article article = articleForm.toEntity();
        log.info("article id ={}", article.getId());

        // 수정한 엔티티의 아이디와 동일한 겟물이 존재하는지 체크하려고 만듬
        Article target = articleRepository.findById(articleForm.getId()).orElse(null);

        //만약 디비에 수정하려는 게시글과 동일한 게시글이 존재시 폼데이터가 담긴 객체를 세이브 한다.
        if(target != null) {
             articleRepository.save(article);}
        else {
            throw new NullPointerException("article에 변경 데이터가 담기지 않았습니다.");
        }

        return "redirect:/articles/"+articleForm.getId();
    }

    //삭제하기
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        //디비에서 해당 아이디 찾기
        Article article = articleRepository.findById(id).orElse(null);
        log.info("article id ={}", article.getId());

        if(article != null) {
            articleRepository.delete(article);
            redirectAttributes.addFlashAttribute("msg","삭제완료되었습니다.");

        }else {
            throw new NullPointerException("존재하지 않는 게시물입니다.");
        }
        return "redirect:/article";
    }

}
