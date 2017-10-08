package com.nbsaw.miaohu.service;

import com.nbsaw.miaohu.dao.repository.model.Tag;
import java.util.List;

public interface TagService {

    boolean exists(String tagName);

    void save(String tagName,String avatar,String bio);

    List<Tag> search(String tagName);

    Tag findByName(String tagName);
}
