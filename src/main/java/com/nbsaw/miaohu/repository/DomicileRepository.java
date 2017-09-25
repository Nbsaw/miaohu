package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.domain.Domicile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DomicileRepository extends CrudRepository<Domicile,Long> {
    List<Domicile> findAllByUid(String uid);
}
