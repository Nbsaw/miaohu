package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.domain.Education;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EducationRepository extends CrudRepository<Education,Long> {
    List<Education> findAllByUid(String uid);
}
