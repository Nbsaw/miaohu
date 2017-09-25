package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User,String> {


    /**
     * ---------------------------------------------------------------------------
     *
     *                                 查 找
     *
     * ---------------------------------------------------------------------------
     */

    // 判断手机号码用户是否存在
    @Query("select count(u) > 0 from User u where u.phone = :phone")
    boolean isUserExists(@Param("phone") String phone);

    // 通过手机号码和密码判断账号是否正确
    @Query("select u from User u where u.phone = :phone and u.password = md5(:password)")
    User login(@Param(("phone")) String phone,@Param("password") String password);

    /**
     * ---------------------------------------------------------------------------
     *
     *                                 修 改
     *
     * ---------------------------------------------------------------------------
     */

    // 通过用户id更新密码
    @Transactional
    @Modifying
    @Query("update User u set u.password = :password where u.id = :uid")
    Integer updatePasswordByUid(@Param("uid") String id,@Param("password") String password);
}
