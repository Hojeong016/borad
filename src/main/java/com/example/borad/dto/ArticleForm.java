package com.example.borad.dto;

import com.example.borad.entity.Article;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleForm {

    private long id;
    private String title;
    private String content;


    //dto -> 엔티티로 변환
    public Article toEntity() {
        return new Article(id, title, content);
    }
}
