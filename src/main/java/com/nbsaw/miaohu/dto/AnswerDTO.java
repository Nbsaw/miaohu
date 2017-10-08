package com.nbsaw.miaohu.dto;

import com.nbsaw.miaohu.dao.repository.model.Answer;
import lombok.Data;

import java.io.Serializable;

@Data
public class AnswerDTO implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String content;

    private Long vote;

    public AnswerDTO(Answer answer, Long answerVote){
        this.id = answer.getId();
        if (answer.isAnonymous()){
            this.username = "匿名用户";
            this.avatar   = "http://7xqvgr.com1.z0.glb.clouddn.com/01.png";
        }
        else{
            this.username = answer.getUser().getUsername();
            this.avatar   = answer.getUser().getAvatar();
        }
        this.content = answer.getContent();
        this.vote = answerVote;
    }

}