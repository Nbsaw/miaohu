package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

    @Query("select count(u) > 0 from User u where u.phone = :phone")
    boolean isUserExists(@Param("phone") String phone);

    @Query("select u from User u where u.phone = :phone and u.password = md5(:password)")
    User login(@Param(("phone")) String phone,@Param("password") String password);

    @Transactional
    @Modifying
    @Query("update User u set u.password = :password where u.id = :uid")
    Integer updatePasswordByUid(@Param("uid") String id,@Param("password") String password);

}
