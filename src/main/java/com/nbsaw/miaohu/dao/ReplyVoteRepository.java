package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.ReplyVote;
import org.springframework.data.repository.CrudRepository;

public interface ReplyVoteRepository extends CrudRepository<ReplyVote,Long> {

    ReplyVote findByReplyIdAndUid(Long replyId, String uid);

}
