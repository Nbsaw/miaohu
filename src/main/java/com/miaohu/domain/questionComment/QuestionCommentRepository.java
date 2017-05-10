package com.miaohu.domain.questionComment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Created by Nbsaw on 17-5-3.
 */
public interface QuestionCommentRepository extends Repository<QuestionCommentEntity,Long> {
    // 保存
    void save(QuestionCommentEntity questionCommentEntity);
    // 查找评论
    List<QuestionCommentEntity> findAllByQuestionId(Long questionId, String type);
    // TODO
    // 检测是否回答过问题
    @Query("select count(qc) > 0 from QuestionCommentEntity qc where qc.uid = :uid")
    boolean isReply(@Param("uid") String uid);
    // 删除回答
    // 撤销删除
    // 修改回答

}
