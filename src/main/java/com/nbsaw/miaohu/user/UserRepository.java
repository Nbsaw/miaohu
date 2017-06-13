package com.nbsaw.miaohu.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by fz on 17-3-29.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,String> {

    @Query(value = "select count(u) > 0 from UserEntity u where u.phone = :phone")
    boolean isUserExists(@Param("phone") String phone);

    @Query("select u from UserEntity u where u.phone = :phone and u.password = :password")
    UserEntity login(@Param(("phone")) String phone,@Param("password") String password);
}
