package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.ErrorsMap;
import com.nbsaw.miaohu.common.JsonResult;
import com.nbsaw.miaohu.dao.repository.TagRepository;
import com.nbsaw.miaohu.service.TagService;
import com.nbsaw.miaohu.validator.TagValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class TagController {

    private final TagRepository tagRepository;
    private final TagValidator tagValidator;
    private final TagService tagService;

    // 查找所有的标签
    @GetMapping
    public JsonResult findAll(){
        return new JsonResult(0,"",tagRepository.findAll());
    }

    // 增加新的标签
    @PostMapping("/add")
    public JsonResult add(@RequestParam String tagName,
                          @RequestParam(required = false,defaultValue = "") String bio,
                          @RequestParam(required = false,defaultValue = "http://7xqvgr.com1.z0.glb.clouddn.com/defaultTag.png") String avatar){

        ErrorsMap errors = tagValidator.validTag(tagName);
        if (errors.hasError()){
            return new JsonResult(400,"标签创建失败",errors);
        }
        else{
            tagService.save(tagName,avatar,bio);
            return new JsonResult(0,"标签创建成功");
        }
    }

    // 根据名字查找标签
    @GetMapping("/search/{tagName}")
    public JsonResult findByNameLike(@PathVariable String tagName){
        return new JsonResult(0,"",tagService.search(tagName));
    }


    // 根据名字查找标签下的所有问题以及文章
    // TODO 还没有改


    // 修改文章、问题的标签
}
