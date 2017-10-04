package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.domain.Employments;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmploymentsRepository extends CrudRepository<Employments,Long>{
    List<Employments> findAllByUid(String uid);
}
