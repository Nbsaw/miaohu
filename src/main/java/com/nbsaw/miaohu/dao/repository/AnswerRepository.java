package com.nbsaw.miaohu.dao.repository;

import com.nbsaw.miaohu.dao.repository.model.Answer;
import com.nbsaw.miaohu.dao.repository.model.Question;
import com.nbsaw.miaohu.dao.repository.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface AnswerRepository extends PagingAndSortingRepository<Answer,Long> {

    boolean existsByQuestion_IdAndUser_Id(Long questionId, Long userId);

    List<Answer> findAllByQuestion_Id(Long questionId);
//
//    boolean existsByQuestionIdAndUid(Long questionId,Long uid);
//
//    @Transactional
//    @Modifying
//    @Query("update Answer qc set qc.content = :content where qc.questionId = :questionId and qc.uid = :uid")
//    Integer updateComment(@Param("questionId") Long questionId,@Param("uid") String uid,@Param("content") String content);
//
//    @Transactional
//    @Modifying
//    @Query("update Answer qc set qc.anonymous = :anonymous where qc.questionId = :questionId and qc.uid = :uid")
//    Integer setAnonymous(@Param("questionId") Long questionId, @Param("uid") Long uid,@Param("anonymous") boolean anonymous);

}
