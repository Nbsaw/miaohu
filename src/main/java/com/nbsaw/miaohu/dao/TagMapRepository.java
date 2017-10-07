package com.nbsaw.miaohu.dao;

import com.nbsaw.miaohu.model.TagMap;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagMapRepository extends CrudRepository<TagMap,Long> {

    List<TagMap> findAllByCorrelationAndType(Long correlation,String type);

}
