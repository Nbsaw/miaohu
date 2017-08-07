package com.nbsaw.miaohu.repository;

import com.nbsaw.miaohu.entity.EducationEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EducationRepository extends CrudRepository<EducationEntity,Long> {
    List<EducationEntity> findAllByUid(String uid);
}
