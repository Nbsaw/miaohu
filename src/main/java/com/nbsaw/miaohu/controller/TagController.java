package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.entity.TagEntity;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.util.JsonUtil;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nbsaw on 17-5-5.
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagMapRepository tagMapRepository;
    @Autowired
    private QuestionRepository questionRepository;

    // 查找所有的标签
    @GetMapping
    public ResultVo findAll(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(tagRepository.findAll());
        return resultVo;
    }

    // 增加新的标签
    @PostMapping(value = "/add")
    public MessageVo add(@RequestParam(name = "tagName") String tagName,
                         @RequestParam(name = "bio",defaultValue = "") String bio,
                         @RequestParam(name = "avatar",defaultValue = "http://7xqvgr.com1.z0.glb.clouddn.com/defaultTag.png") String avatar){
        MessageVo result = new MessageVo();
        if (tagRepository.existsName(tagName)){
            result.setCode(400);
            result.setMessage("标签已存在!");
        }else{
            TagEntity tagEntity = new TagEntity();
            tagEntity.setName(tagName);
            tagEntity.setAvatar(avatar);
            tagEntity.setBio(bio);
            tagRepository.save(tagEntity);
            result.setCode(200);
            result.setMessage("创建标签成功!");
        }
        return result;
    }

    // 根据id查找
    // TODO 对应Id标签不存在的处理
    @GetMapping(value = "/{id}")
    public GenericVo findById(@PathVariable("id") Long id){
        List<TagMapEntity> tagMapEntities =  tagMapRepository.findAllByTagId(id);
        List result = new LinkedList();
        tagMapEntities.stream().forEach(map->{
            if (map.getType().equals("question"))
            result.add(questionRepository.findById(map.getCorrelation()));
        });
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // 根据名字查找标签
    @GetMapping(value = "/search/{name}")
    public ResultVo findByNameLike(@PathVariable("name") String name){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(tagRepository.findAllByNameLike(name));
        return resultVo;
    }
}
