package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.repository.ArticleRepository;
import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.entity.TagEntity;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.QAResultVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/tag")
class TagController {

    @Autowired private TagRepository tagRepository;
    @Autowired private TagMapRepository tagMapRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private ArticleRepository articleRepository;

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
        }
        else if (tagName.trim().isEmpty()){
            result.setCode(400);
            result.setMessage("标签名不能为空!");
        }
        else if (tagName.contains(" ")){
            result.setCode(400);
            result.setMessage("标签不能包含空格!");
        }
        else{
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

    // TODO 对应名字标签不存在的处理
    // 根据名字查找标签下的所有问题以及文章
    @GetMapping(value = "/{tagName}")
    public GenericVo findById(@PathVariable("tagName") String tagName){
        TagEntity tag = tagRepository.findByNameIgnoreCase(tagName);
        if (tag == null){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(404);
            messageVo.setMessage("标签不存在");
            return messageVo;
        }else{
            ResultVo resultVo = new ResultVo();

            return resultVo;
        }
    }

    // 根据名字查找标签
    @GetMapping(value = "/search/{tagName}")
    public ResultVo findByNameLike(@PathVariable("tagName") String tagName){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(tagRepository.findAllByNameLike(tagName));
        return resultVo;
    }

    // 修改文章、问题的标签
}
