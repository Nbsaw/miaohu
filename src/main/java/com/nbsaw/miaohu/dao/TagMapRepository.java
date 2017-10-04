package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.domain.TagMap;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagMapRepository extends CrudRepository<TagMap,Long> {

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 根据帖子id和类型找标签
    List<TagMap> findAllByCorrelationAndType(Long correlation,String type);



}
