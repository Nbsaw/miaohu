package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.TagMapEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagMapRepository extends CrudRepository<TagMapEntity,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 根据帖子id和类型找标签
    List<TagMapEntity> findAllByCorrelationAndType(Long correlation,String type);



}
