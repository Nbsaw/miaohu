package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends PagingAndSortingRepository<Question,Long> {

    List<Question> findAllByUid(String uid,Pageable page);

    @Query("select count(q) > 0 from Question q where q.id = :id")
    boolean isExists(@Param("id") Long id);

    boolean existsByIdAndUid(@Param("id")Long id,@Param("uid")String uid);

    boolean existsByTitle(@Param("title") String title);

    @Query("select qc.anonymous from Question qc where qc.id = :id")
    boolean isAnonymous(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update Question q set q.title = :title , q.content = :content , q.anonymous =:anonymous where q.id = :id and q.uid = :uid ")
    Integer updateContentByIdAndUid(@Param("id") Long id, @Param("uid") String uid,@Param("title") String title,@Param("content") String content,@Param("anonymous") boolean anonymous);

    @Transactional
    @Modifying
    @Query("update Question q set q.anonymous = :anonymous where q.id = :id and q.uid = :uid")
    Integer setAnonymous(@Param("id") Long id, @Param("uid") String uid,@Param("anonymous") boolean anonymous);

    @Transactional
    Integer deleteById(Long id);
}
