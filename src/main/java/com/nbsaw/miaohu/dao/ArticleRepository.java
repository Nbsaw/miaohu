package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends PagingAndSortingRepository<Article,Long> {

    @Query("select count(a) > 0 from Article a where a.id = :id and a.uid = :uid")
    boolean belong(@Param("id")Long id, @Param("uid")String uid);

}
