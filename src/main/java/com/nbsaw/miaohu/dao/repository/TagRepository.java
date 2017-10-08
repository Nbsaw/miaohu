package com.nbsaw.miaohu.dao.repository;

import com.nbsaw.miaohu.dao.repository.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TagRepository extends CrudRepository<Tag,Long> {

    boolean existsByName(String name);

    List<Tag> findAllByNameContains(@Param("name") String name);

    Tag findByName(String name);

}
