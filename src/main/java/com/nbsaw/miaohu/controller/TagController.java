package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.dao.*;
import com.nbsaw.miaohu.domain.Tag;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.QAResultVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired private TagRepository  tagRepository;

    // 查找所有的标签
    @GetMapping
    public ResultVo findAll(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(tagRepository.findAll());
        return resultVo;
    }

    // 增加新的标签
    @PostMapping("/add")
    public MessageVo add(@RequestParam String tagName,
                         @RequestParam(defaultValue = "") String bio,
                         @RequestParam(defaultValue = "http://7xqvgr.com1.z0.glb.clouddn.com/defaultTag.png") String avatar){
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
            Tag tag = new Tag();
            tag.setName(tagName);
            tag.setAvatar(avatar);
            tag.setBio(bio);
            tagRepository.save(tag);
            result.setCode(200);
            result.setMessage("创建标签成功!");
        }
        return result;
    }


    // TODO 对应名字标签不存在的处理
    // 根据名字查找标签下的所有问题以及文章
    @GetMapping("/{tagName}")
    public GenericVo findById(@PathVariable String tagName){
        Tag tag = tagRepository.findByNameIgnoreCase(tagName);
        if (StringUtils.isEmpty(tagName)){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(404);
            messageVo.setMessage("标签不存在");
            return messageVo;
        }else{
            ResultVo resultVo = new ResultVo();
            List<QAResultVo> qaResultVos = new ArrayList<>();
            tagRepository.findQA().forEach(l -> {
                String id      = String.valueOf(l[0]);
                String title   = String.valueOf(l[1]);
                String content = String.valueOf(l[2]);
                String uid     = String.valueOf(l[3]);
                String date    = String.valueOf(l[4]);
                String type    = String.valueOf(l[5]);
                qaResultVos.add(new QAResultVo(id,title,content,uid,date,type));
            });
            resultVo.setResult(qaResultVos);
            return resultVo;
        }
    }

    // 根据名字查找标签
    @GetMapping("/search/{tagName}")
    public ResultVo findByNameLike(@PathVariable String tagName){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(tagRepository.findAllByNameContains(tagName));
        return resultVo;
    }

    // 修改文章、问题的标签
}
