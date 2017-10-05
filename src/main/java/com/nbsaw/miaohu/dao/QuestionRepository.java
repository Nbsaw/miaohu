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

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 通过Uid查找问题列表
    List<Question> findAllByUid(String uid,Pageable page);

    // 通过id判断问题是否存在
    @Query("select count(q) > 0 from Question q where q.id = :id")
    boolean isExists(@Param("id") Long id);

    // 根据uid和帖子id查找问题归属
    @Query("select count(q) > 0 from Question q where q.id = :id and q.uid =:uid")
    boolean belong(@Param("id")Long id,@Param("uid")String uid);

    // 判断问题是否已经存在
    @Query("select count(q) > 0 from Question q where q.title = :title")
    boolean existsByTitle(@Param("title") String title);

    // 查询问题是否为匿名
    @Query("select qc.anonymous from Question qc where qc.id = :id")
    boolean isAnonymous(@Param("id") Long id);


    /**
     * ---------------------------------------------------------------------------
     *
     *                                 修 改
     *
     * ---------------------------------------------------------------------------
     */

    // 修改问题
    @Transactional
    @Modifying
    @Query("update Question q set q.title = :title , q.content = :content where q.id = :id and q.uid = :uid ")
    Integer updateContentByIdAndUid(@Param("id") Long id, @Param("uid") String uid,@Param("title") String title,@Param("content") String content);


    // 把问题设置为匿名
    @Transactional
    @Modifying
    @Query("update Question q set q.anonymous = true where q.id = :id and q.uid = :uid")
    Integer setAnonymousTrue(@Param("id") Long id, @Param("uid") String uid);

    // 把问题设置为实名
    @Transactional
    @Modifying
    @Query("update Question q set q.anonymous = false where q.id = :id and q.uid = :uid")
    Integer setAnonymousFalse(@Param("id") Long id, @Param("uid") String uid);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 删 除
     *
     * ---------------------------------------------------------------------------
     */

    // 删除问题
    @Transactional
    Integer deleteById(Long id);
}
