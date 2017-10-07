package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.ArticleVote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ArticleVoteRepository extends CrudRepository<ArticleVote,Long> {

    @Query("select count(v) > 0 from ArticleVote v where v.articleId = :articleId and v.uid = :uid")
    boolean isVoted(@Param("articleId") Long articleId,@Param("uid") String uid);

    @Transactional
    Integer deleteByArticleIdAndUid(Long articleId,String uid);

}
