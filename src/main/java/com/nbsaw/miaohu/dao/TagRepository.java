package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.domain.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TagRepository extends CrudRepository<Tag,Long> {
    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 根据名字判断是否存在
    @Query("select count(t) > 0 from Tag t where lower(t.name) = lower(:name)")
    boolean existsName(@Param("name") String name);

    // 通过id查找
    Tag findById(Long id);

    // 通过相似的关键字查找标签
    List<Tag> findAllByNameContains(@Param("name") String name);

    // 通过名字查找标签
    Tag findByNameIgnoreCase(String name);


    // 问题文章表合并查询
    // TODO 非常不好的实现。需要更改
    // id,title,content,uid,date
//    @Query("select new com.nbsaw.miaohu.vo.QAResultVo(q.id,q.title,q.content,q.uid,q.date,'question') from Article q order by date")
//    @Query(value = "select * from (select q.id,q.title,q.content,q.uid,q.date,'question' as 'type' from question q union all select a.id,a.title,a.content,a.uid,a.date,'article' as 'type' from article a) j;",nativeQuery = true)
    @Query(value = "select r.id,r.title,r.content,r.uid,r.date,r.type from (select q.id,q.title,q.content,q.uid,q.date,'question' as 'type' from question q union all select a.id,a.title,a.content,a.uid,a.date,'article' as 'type' from article a) r order by date DESC ",nativeQuery = true)
    List<Object[]> findQA();

}
