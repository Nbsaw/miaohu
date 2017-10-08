package com.nbsaw.miaohu.dto;

import com.nbsaw.miaohu.dao.repository.model.Answer;
import com.nbsaw.miaohu.dao.repository.model.Question;
import com.nbsaw.miaohu.dao.repository.model.Tag;
import com.nbsaw.miaohu.dao.repository.model.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class QuestionDetailedDTO {

    private Long id;

    private _User user;

    private List<Tag> tags;

    private String title;

    private String content;

    private Date creationDate;

    private Long vote;

    private boolean replied;

    private List<_Answer> answers;

    @Data
    private class _User implements Serializable {

        private String username;

        private String bio;

        private String avatar;

        public _User(User user){
            this.username = user.getUsername();
            this.bio      = user.getBio();
            this.avatar   = user.getAvatar();
        }

    }

    @Data
    private class _Answer implements Serializable{

        private Long id;

        private String username;

        private String avatar;

        private String content;

        private Long vote;

        public _Answer(Answer answer,Long answerVote){
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

    public QuestionDetailedDTO(Question question, Long vote, boolean replied,List<Answer> an,List<Long> answersVote){
        this.id           = question.getId();
        this.tags         = question.getTags();
        this.title        = question.getTitle();
        this.content      = question.getContent();
        this.creationDate = question.getCreationDate();
        this.user         = new _User(question.getUser());
        this.vote         = vote;
        this.replied      = replied;
        this.answers       = new LinkedList<>();
        for (int i = 0 ; i < an.size() ; i++)
            answers.add(new _Answer(an.get(i),answersVote.get(i)));
    }
}
