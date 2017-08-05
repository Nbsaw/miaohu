package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.ReplyEntity;
import com.nbsaw.miaohu.entity.ReplyVoteEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReplyVoteRepository extends CrudRepository<ReplyVoteEntity,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */
    ReplyVoteEntity findByReplyIdAndUid(Long replyId, String uid);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 增 加
     *
     * ---------------------------------------------------------------------------
     */

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 删 除
     *
     * ---------------------------------------------------------------------------
     */
}
