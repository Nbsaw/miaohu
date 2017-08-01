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

    // 根据id和类型查找
    List<TagMapEntity> findAllByTagIdAndType(Long id,String type);

    // 根据帖子id找标签
    List<TagMapEntity> findAllByCorrelation(Long correlation);

}
