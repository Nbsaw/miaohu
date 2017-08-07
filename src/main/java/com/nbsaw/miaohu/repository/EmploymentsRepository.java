package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.EmploymentsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmploymentsRepository extends CrudRepository<EmploymentsEntity,Long>{
    List<EmploymentsEntity> findAllByUid(String uid);
}
