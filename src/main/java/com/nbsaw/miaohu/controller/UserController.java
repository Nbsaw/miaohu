package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.entity.QuestionEntity;
import com.nbsaw.miaohu.entity.UserEntity;
import com.nbsaw.miaohu.repository.UserRepository;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import com.nbsaw.miaohu.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
class UserController {

    @Autowired private UserRepository     userRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private TagRepository      tagRepository;
    @Autowired private TagMapRepository   tagMapRepository;
    @Autowired private JwtUtil            jwtUtil;


    // TODO 第三方登录
    // 登录验证
    @PostMapping(value = "/login")
    public GenericVo login(@RequestParam("phone") String phone , @RequestParam("password") String password){
        // 检查手机号码是否存在
        if (userRepository.isUserExists(phone)){
            try{
                // 校对用户手机号码密码
                UserEntity userEntity = userRepository.login(phone,password);
                ResultVo resultVo = new ResultVo();
                resultVo.setCode(200);
                String token = jwtUtil.createJWT(userEntity.getId(),userEntity.getUserType());
                resultVo.setResult(token);
            return resultVo;}
            catch (Exception e){
                MessageVo messageVo = new MessageVo();
                messageVo.setCode(400);
                messageVo.setMessage("用户密码错误");
                return messageVo;
            }
        }
        else{
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("用户不存在");
            return messageVo;
        }
    }

    // 获取用户信息
    @GetMapping(value = "/info")
    public GenericVo information(HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // TODO 从Redis获取id，再从数据库查信息 -> 主要是判断用户是否注销了
        try {
            // 获取uid
            String uid = jwtUtil.getUid(request);

            // 查询
            UserEntity userEntity = userRepository.findAllById(uid);
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setUsername(userEntity.getUsername());
            userInfoVo.setAvatar(userEntity.getAvatar());
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

    // TODO 让token失效
    // 修改用户密码
    @PostMapping(value = "/changePassword")
    public MessageVo changePassword(HttpServletRequest request, @RequestParam("password") String password) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

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

    // TODO 分页
    // 获取用户发表过的问题
    @GetMapping(value = "/question")
    public ResultVo question(HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 获取uid
        String uid = jwtUtil.getUid(request);

        List result = new ArrayList();
        // 查找用户发表的问题
        List<QuestionEntity> questionEntities =  questionRepository.findAllByUid(uid);
        // 查找问题所属的标签
        questionEntities.forEach(q ->{
            // 创建一个映射
            Map map = new LinkedHashMap();
            List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByCorrelationAndType(q.getId(),"question");
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

    // TODO 资料修改

    // TODO 注销登陆
    // 从redis删除token
}