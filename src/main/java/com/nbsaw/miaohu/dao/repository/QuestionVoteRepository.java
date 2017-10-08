package com.nbsaw.miaohu.dao.repository;

import com.nbsaw.miaohu.dao.repository.model.Question;
import com.nbsaw.miaohu.dao.repository.model.QuestionVote;
import org.springframework.data.repository.CrudRepository;

public interface QuestionVoteRepository extends CrudRepository<QuestionVote,Long> {

    Long countAllByQuestion(Question question);

}
