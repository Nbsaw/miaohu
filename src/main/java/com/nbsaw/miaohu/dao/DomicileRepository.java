package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.Domicile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DomicileRepository extends CrudRepository<Domicile,Long> {
    List<Domicile> findAllByUid(String uid);
}
