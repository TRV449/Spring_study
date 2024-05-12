package com.example.spring_study.dto;

import com.example.spring_study.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id; //댓글의 id
    private Long articleId; //해당 댓글의 부모 게시글
    private String nickname; //댓글 작성자
    private String body; //댓글 내용

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
