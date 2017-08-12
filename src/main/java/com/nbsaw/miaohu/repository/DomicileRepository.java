package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.DomicileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DomicileRepository extends CrudRepository<DomicileEntity,Long> {
    List<DomicileEntity> findAllByUid(String uid);
}
