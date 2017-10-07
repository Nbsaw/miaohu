package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.Answer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer,Long> {

    List<Answer> findAllByQuestionId(Long questionId,Pageable pageable);

    @Query("select count(a) > 0 from Answer a where a.questionId = :questionId and a.uid = :uid")
    boolean isSelf(@Param("questionId") Long questionId,@Param("uid") String uid);

    @Query("select count(qc) > 0 from Answer qc where qc.questionId = :questionId and qc.uid = :uid")
    boolean isExists(@Param("questionId") Long questionId , @Param("uid") String uid);

    @Query("select qc.deleted from Answer qc where qc.questionId = :questionId and qc.uid = :uid ")
    boolean isDeleted(@Param("questionId") Long questionId,@Param("uid") String uid);

    @Query("select qc.anonymous from Answer qc where qc.questionId = :questionId and qc.uid = :uid")
    boolean isAnonymous(@Param("questionId") Long questionId,@Param("uid") String uid);

    @Transactional
    @Modifying
    @Query("update Answer qc set qc.content = :content where qc.questionId = :questionId and qc.uid = :uid")
    Integer updateComment(@Param("questionId") Long questionId,@Param("uid") String uid,@Param("content") String content);

    @Transactional
    @Modifying
    @Query("update Answer qc set qc.anonymous = :anonymous where qc.questionId = :questionId and qc.uid = :uid")
    Integer setAnonymous(@Param("questionId") Long questionId, @Param("uid") String uid,@Param("anonymous") boolean anonymous);

    @Transactional
    @Modifying
    @Query("update Answer qc set qc.deleted = true where qc.questionId = :questionId and qc.uid = :uid")
    Integer setDeletedTrue(@Param("questionId") Long questionId,@Param("uid") String uid);

    @Transactional
    @Modifying
    @Query("update Answer qc set qc.deleted = false where qc.questionId = :questionId and qc.uid = :uid")
    Integer setDeletedFalse(@Param("questionId") Long questionId,@Param("uid") String uid);

}
