package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TagRepository extends CrudRepository<Tag,Long> {

    boolean existsByName(@Param("name") String name);

    Tag findById(Long id);

    List<Tag> findAllByNameContains(@Param("name") String name);

    Tag findByNameIgnoreCase(String name);

    // 问题文章表合并查询
    // TODO 非常不好的实现。需要更改
    // id,title,content,uid,date
//    @Query("select new com.nbsaw.miaohu.vo.QAResultVo(q.id,q.title,q.content,q.uid,q.date,'question') from Article q order by date")
//    @Query(value = "select * from (select q.id,q.title,q.content,q.uid,q.date,'question' as 'type' from question q union all select a.id,a.title,a.content,a.uid,a.date,'article' as 'type' from article a) j;",nativeQuery = true)
    @Query(value = "select r.id,r.title,r.content,r.uid,r.date,r.type from (select q.id,q.title,q.content,q.uid,q.date,'question' as 'type' from question q union all select a.id,a.title,a.content,a.uid,a.date,'article' as 'type' from article a) r order by date DESC ",nativeQuery = true)
    List<Object[]> findQA();
}
