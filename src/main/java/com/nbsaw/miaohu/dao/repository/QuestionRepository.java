package com.nbsaw.miaohu.dao.repository;

import com.nbsaw.miaohu.dao.repository.model.Question;
import com.nbsaw.miaohu.dao.repository.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

public interface QuestionRepository extends PagingAndSortingRepository<Question,Long> {

    boolean existsByIdAndUser(Long id , User user);

    boolean existsByTitle(String title);

//
//    @Query("select qc.anonymous from Question qc where qc.id = :id")
//    boolean isAnonymous(@Param("id") Long id);
//
//    @Transactional
//    @Modifying
//    @Query("update Question q set q.title = :title , q.content = :content , q.anonymous =:anonymous where q.id = :id and q.uid = :uid ")
//    Integer updateContentByIdAndUid(@Param("id") Long id, @Param("uid") Long uid,@Param("title") String title,@Param("content") String content,@Param("anonymous") boolean anonymous);
//
//    @Transactional
//    @Modifying
//    @Query("update Question q set q.anonymous = :anonymous where q.id = :id and q.uid = :uid")
//    Integer setAnonymous(@Param("id") Long id, @Param("uid") Long uid,@Param("anonymous") boolean anonymous);
//
//    @Transactional
//    Integer deleteById(Long id);
}
