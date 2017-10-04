package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.domain.ReplyVote;
import org.springframework.data.repository.CrudRepository;

public interface ReplyVoteRepository extends CrudRepository<ReplyVote,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */
    ReplyVote findByReplyIdAndUid(Long replyId, String uid);

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
