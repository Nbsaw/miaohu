package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.ReplyEntity;
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
    List<ReplyEntity> findAllByArticleId(Long articleId);

    @Query("select count(r) > 0 from ReplyEntity r where r.id = :id and r.uid = :uid")
    boolean belong(@Param("id") Long id, @Param("uid") String uid);

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
