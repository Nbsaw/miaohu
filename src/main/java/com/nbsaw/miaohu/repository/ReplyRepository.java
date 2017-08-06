package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.ReplyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends CrudRepository<ReplyEntity,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */
    List<ReplyEntity> findAllByArticleIdAndPass(Long articleId,boolean pass,Pageable page);

    @Query("select count(r) > 0 from ReplyEntity r where r.id = :id and r.uid = :uid")
    boolean belong(@Param("id") Long id, @Param("uid") String uid);

    // 判断是否是作者
    // TODO 看看有没有hql的实现
    @Query(value = "select count(*) > 0 from reply r join article a on (r.id = :id and r.article_id = a.id and a.uid = :uid)",nativeQuery = true)
    Integer isAuthor(@Param("id") Long id,@Param("uid") String uid);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 修 改
     *
     * ---------------------------------------------------------------------------
     */

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 增 加
     *
     * ---------------------------------------------------------------------------
     */
}
