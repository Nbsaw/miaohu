package com.nbsaw.miaohu.dao.repository;

import com.nbsaw.miaohu.dao.repository.model.Answer;
import com.nbsaw.miaohu.dao.repository.model.AnswerVote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface AnswerVoteRepository extends CrudRepository<AnswerVote,Long> {

    long countAllByAnswer(Answer answer);

    boolean existsByAnswer_IdAndUser_Id(Long answerId,Long uid);

    @Transactional
    @Modifying
    void deleteByAnswer_IdAndUser_Id(Long answerId,Long uid);

}