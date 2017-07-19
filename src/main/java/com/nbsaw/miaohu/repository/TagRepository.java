package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TagRepository extends Repository<TagEntity,Long> {
    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 根据名字判断是否存在
    @Query("select count(t) > 0 from TagEntity t where lower(t.name) = lower(:name)")
    boolean existsName(@Param("name") String name);

    // 通过id查找
    TagEntity findById(Long id);

    // 显示以及存在的所有标签
    List<TagEntity> findAll();

    // 通过相似的关键字查找标签
    @Query("select t from TagEntity t where t.name like CONCAT('%',:name,'%')")
    List<TagEntity> findAllByNameLike(@Param("name") String name);

    // 通过名字查找标签
    @Query("select t from TagEntity t where lower(t.name) = lower(:name)")
    TagEntity findByName(@Param("name") String name);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 保 存
     *
     * ---------------------------------------------------------------------------
     */

    // 保存一个标签
    void save(TagEntity tagEntity);
}
