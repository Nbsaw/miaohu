package com.nbsaw.miaohu.web;

import com.nbsaw.miaohu.common.JwtUtils;
import com.nbsaw.miaohu.dao.*;
import com.nbsaw.miaohu.model.Question;
import com.nbsaw.miaohu.model.TagMap;
import com.nbsaw.miaohu.model.User;
import com.nbsaw.miaohu.type.SexType;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import com.nbsaw.miaohu.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class UserInfoController {

    UserRepository        userRepository;
    QuestionRepository    questionRepository;
    TagRepository         tagRepository;
    TagMapRepository      tagMapRepository;
    JwtUtils jwtUtils;
    EducationRepository   educationRepository;
    EmploymentsRepository employmentsRepository;
    DomicileRepository    domicileRepository;

    // 获取用户信息
    @GetMapping("/info")
    public GenericVo information(@RequestHeader("${jwt.header}") String token) {
        // TODO 从Redis获取id，再从数据库查信息 -> 主要是判断用户是否注销了
        try {
            String uid = jwtUtils.getUid(token);
            // 查询用户信息
            User user = userRepository.findOne(uid);
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setUsername(user.getUsername());
            userInfoVo.setSex(user.getSex().getValue());
            userInfoVo.setAvatar(user.getAvatar());
            userInfoVo.setBio(user.getBio());
            userInfoVo.setIndustry(user.getIndustry());
            userInfoVo.setResume(user.getResume());
            // 查询教育经历
            userInfoVo.setEducation(educationRepository.findAllByUid(uid));
            // 查询工作经历
            userInfoVo.setEmployments(employmentsRepository.findAllByUid(uid));
            // 查询居住地
            userInfoVo.setDomicile(domicileRepository.findAllByUid(uid));
            ResultVo resultVo = new ResultVo();
            resultVo.setCode(200);
            resultVo.setResult(userInfoVo);
            return resultVo;
        }catch (NullPointerException e){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("用户名或者密码错误");
            return messageVo;
        }
    }

    // TODO token 黑名单
    // 修改用户密码
    @PostMapping("/changePassword")
    public MessageVo changePassword(@RequestParam String password,
                                    @RequestHeader("${jwt.header}") String token) {
        String uid = jwtUtils.getUid(token);
        // 返回的数据
        MessageVo messageVo = new MessageVo();
        // 修改密码
        Boolean status = userRepository.updatePasswordByUid(uid,password) == 1;
        if (status){
            messageVo.setCode(200);
            messageVo.setMessage("修改密码成功！");
        }
        else{
            messageVo.setCode(400);
            messageVo.setMessage("修改密码失败！");
        }
        return messageVo;
    }

    // 获取用户发表过的问题
    @GetMapping(value = "/question")
    public ResultVo question(@RequestParam(defaultValue = "0") int page,
                             @RequestHeader("${jwt.header}") String token) {
        String uid = jwtUtils.getUid(token);
        List result = new ArrayList();
        // 查找用户发表的问题
        List<Question> questionEntities =  questionRepository.findAllByUid(uid,new PageRequest(page,15,new Sort(Sort.Direction.DESC,"date")));
        // 查找问题所属的标签
        questionEntities.forEach(q ->{
            // 创建一个映射
            Map map = new LinkedHashMap();
            List<TagMap> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(q.getId(),"question");
            // 搜索标签
            List tagList = new LinkedList();
            tagMapEntities.forEach(m -> tagList.add(tagRepository.findById(m.getCorrelation())));
            // 把问题加入映射
            map.put("question",q);
            // 添加问题映射
            map.put("tag",tagList);
            // 把映射加到列表
            result.add(map);
        });
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(200);
        resultVo.setResult(result);
        return resultVo;
    }

    // 性别修改
    @PutMapping("/sex")
    public MessageVo changeSex(@RequestBody Map<String,String> obj,
                               @RequestHeader("${jwt.header}") String token) {
        String sex = obj.get("sex");
        // 获取uid
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        User user = userRepository.findOne(uid);
        if (StringUtils.isEmpty(user)){
            messageVo.setCode(HttpStatus.BAD_REQUEST.value());
            messageVo.setMessage("用户不存在");
        }else{
            if (SexType.fromString(sex) != null){
                user.setSex(SexType.valueOf(sex));
                userRepository.save(user);
                messageVo.setCode(HttpStatus.OK.value());
                messageVo.setMessage("性别更改成功");
            }else{
                messageVo.setCode(HttpStatus.BAD_REQUEST.value());
                messageVo.setMessage("不存在性别");
            }
        }
        return messageVo;
    }

    // 一句话介绍
    @PutMapping("/bio")
    public MessageVo changeBio(@RequestBody Map<String,String> obj,
                               @RequestHeader("${jwt.header}") String token) {
        String bio = obj.get("bio");
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        User user = userRepository.findOne(uid);
        if (user == null){
            messageVo.setCode(HttpStatus.BAD_REQUEST.value());
            messageVo.setMessage("用户不存在");
        }
        else if (bio.length() > 40){
            messageVo.setCode(HttpStatus.BAD_REQUEST.value());
            messageVo.setMessage("请在40个字以内描述自己");
        }
        else{
            user.setBio(bio);
            userRepository.save(user);
            messageVo.setCode(HttpStatus.OK.value());
            messageVo.setMessage("一句话介绍自己更改成功");
        }
        return messageVo;
    }

    // 所在行业
    @PutMapping("/industry")
    public MessageVo changeIndustry(@RequestBody Map<String,String> obj,
                                    @RequestHeader("${jwt.header}") String token) {
        String industry = obj.get("industry");
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        User user = userRepository.findOne(uid);
        if (user == null){
            messageVo.setCode(HttpStatus.BAD_REQUEST.value());
            messageVo.setMessage("用户不存在");
        }else{
            user.setIndustry(industry);
            userRepository.save(user);
            messageVo.setCode(HttpStatus.OK.value());
            messageVo.setMessage("行业更改成功");
        }
        return messageVo;
    }

    // 个人简介
    @PutMapping("/resume")
    public MessageVo changeResume(@RequestBody Map<String,String> obj,
                                  @RequestHeader("${jwt.header}") String token) {
        String resume = obj.get("resume");
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        User user = userRepository.findOne(uid);
        if (user == null){
            messageVo.setCode(HttpStatus.BAD_REQUEST.value());
            messageVo.setMessage("用户不存在");
        }
        else if(resume.length() > 550){
            messageVo.setCode(HttpStatus.BAD_REQUEST.value());
            messageVo.setMessage("不能超过550字");
        }
        else{
            user.setResume(resume);
            userRepository.save(user);
            messageVo.setCode(HttpStatus.OK.value());
            messageVo.setMessage("简介更改成功");
        }
        return messageVo;
    }

    // TODO 不允许重复
    // TODO 居住地,包含多个
    @PutMapping("/domicile")
    public MessageVo changeDomicile(@RequestParam("domicile") String domicile,
                                    @RequestHeader("${jwt.header}") String token) {
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        User user = userRepository.findOne(uid);
        if (user == null){
            messageVo.setCode(400);
            messageVo.setMessage("用户不存在");
        }else{

        }
        return messageVo;
    }

    // TODO 职业经历,包含多个
//    @PutMapping("/employments")
//    public MessageVo changeEmployments(@RequestBody Persons persons, HttpServletRequest request) {
//        // 获取uid
//        String uid = jwtUtil.getUid(request);
//        System.out.println(persons.getPerson());
//        MessageVo messageVo = new MessageVo();
//        return messageVo;
//    }

    // TODO 教育经历,包含多个
    @PutMapping("/education")
    public MessageVo changeEducation(@RequestBody String education,
                                     @RequestHeader("${jwt.header}") String token) {
        String uid = jwtUtils.getUid(token);
        MessageVo messageVo = new MessageVo();
        return messageVo;
    }

    // TODO 头像修改

}
