package com.nbsaw.miaohu.dao.repository;

import com.nbsaw.miaohu.dao.repository.model.User;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    boolean existsByPhone(@Param("phone") String phone);

    User findByPhoneAndPassword(String phone , String password);

    @Transactional
    @Modifying
    @Query("update User u set u.password = :password where u.id = :uid")
    int updatePasswordByUid(@Param("uid") Long id,@Param("password") String password);

}
