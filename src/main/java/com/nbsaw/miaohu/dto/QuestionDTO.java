package com.nbsaw.miaohu.dto;

import com.nbsaw.miaohu.dao.repository.model.Question;
import com.nbsaw.miaohu.dao.repository.model.Tag;
import com.nbsaw.miaohu.dao.repository.model.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class QuestionDTO implements Serializable {

    private Long id;

    private _User user;

    private List<Tag> tags;

    private String title;

    private String content;

    private Date creationDate;

    private Long vote;

    @Data
    private class _User implements Serializable{

        private String username;

        private String bio;

        private String avatar;

        public _User(User user){
            this.username = user.getUsername();
            this.bio      = user.getBio();
            this.avatar   = user.getAvatar();
        }

    }

    public QuestionDTO(Question question,Long vote){
        this.id           = question.getId();
        this.tags         = question.getTags();
        this.title        = question.getTitle();
        this.content      = question.getContent();
        this.creationDate = question.getCreationDate();
        this.user         = new _User(question.getUser());
        this.vote         = vote;
    }

}
