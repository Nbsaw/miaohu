package com.zhihu.domain.tag.tagMap;


import com.zhihu.domain.tag.TagEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Nbsaw on 17-5-5.
 */
public interface TagMapRepository extends Repository<TagMapEntity,Long> {
    // 保存
    void save(TagMapEntity entity);
    // 根据id和类型查找
    List<TagMapEntity> findAllByTagIdAndType(Long id,String type);
    // 根据id查找
    List<TagMapEntity> findAllByTagId(Long id);
    // 根据帖子id找标签
    List<TagMapEntity> findAllByCorrelation(Long id);
    // 显示所有的映射
    List<TagEntity> findAll();
}
