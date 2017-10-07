package com.nbsaw.miaohu.serviceImpl;

import com.nbsaw.miaohu.dao.TagRepository;
import com.nbsaw.miaohu.model.Tag;
import com.nbsaw.miaohu.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public boolean exists(String tagName) {
        return tagRepository.existsByName(tagName);
    }

    @Override
    public void save(String tagName, String avatar, String bio) {
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setAvatar(avatar);
        tag.setBio(bio);
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> search(String tagName) {
        return tagRepository.findAllByNameContains(tagName);
    }
}
