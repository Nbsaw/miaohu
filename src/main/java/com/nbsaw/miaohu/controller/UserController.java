package com.nbsaw.miaohu.controller;

import com.nbsaw.miaohu.repository.QuestionRepository;
import com.nbsaw.miaohu.entity.TagMapEntity;
import com.nbsaw.miaohu.repository.TagRepository;
import com.nbsaw.miaohu.entity.QuestionEntity;
import com.nbsaw.miaohu.service.UserInfoService;
import com.nbsaw.miaohu.entity.UserEntity;
import com.nbsaw.miaohu.model.UserInfoModel;
import com.nbsaw.miaohu.repository.UserRepository;
import com.nbsaw.miaohu.type.OauthType;
import com.nbsaw.miaohu.repository.TagMapRepository;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * Created by fz on 17-3-22.
 * 用户rest路由控制器
 * 处理用户的信息
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    // 用户信息
    @Autowired
    private UserInfoService userInfoService;

    // 用户
    @Autowired
    private UserRepository userRepository;

    // 问题
    @Autowired
    private QuestionRepository questionRepository;

    // 标签
    @Autowired
    private TagRepository tagRepository;

    // 标签映射
    @Autowired
    private TagMapRepository tagMapRepository;

    /**
     * 登录接口,判断手机和密码,把信息存在session里面
     * @param phone 手机号码
     * @param password 账号密码
     */
    // TODO 第三方登录
    // TODO 分清楚是密码错误还是账号错误
    @PostMapping(value = "/login")
    public GenericVo login(@RequestParam("phone") String phone , @RequestParam("password") String password){
        try {
            // 查询
            UserEntity userEntity = userRepository.login(phone,password);
            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setUsername(userEntity.getUsername());
            userInfoModel.setAvatar(userEntity.getAvatar());
            ResultVo resultVo = new ResultVo();
            resultVo.setCode(200);
            resultVo.setResult(userInfoModel);
            return resultVo;
        }catch (Exception e){
            MessageVo messageVo = new MessageVo();
            messageVo.setCode(400);
            messageVo.setMessage("用户名或者密码错误");
            return messageVo;
        }
    }

    // 获取用户信息
    @GetMapping(value = "/info")
    public Map information(HttpSession session){
        Map result = new LinkedHashMap();
        // 从Session获取已经存在的用户access_token,用户类型
        try {
            result.put("code",200);
            result.put("data",userInfoService.getUserInfo(session));
        }catch (Exception e){
            result.put("code",401);
            result.put("errors","没登录拿个猫的信息");
        }
        return result;
    }

    // TODO 密码修改
    @PostMapping(value = "/changePassword")
    public String changePassword(@RequestParam("uid") String uid){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setUsername("test");
        userEntity.setPassword("25802580");
        userEntity.setPhone("13164726498");
        userRepository.save(userEntity);
        return "asd";
    }

    // TODO 资料修改

    /**
     * 获取用户发表的所有的问题
     * @param session
     * @return 获取用户发表的所有的问题
     */
    // TODO 分页
    @GetMapping(value = "/question")
    public ResultVo question(HttpSession session){
        String uid = (String) session.getAttribute("id");
        List result = new ArrayList();
        // 查找用户发表的问题
        List<QuestionEntity> questionEntities =  questionRepository.findAllByUid(uid);
        // 查找问题所属的标签
        questionEntities.stream().forEach(q ->{
            // 创建一个映射
            Map map = new LinkedHashMap();
            List<TagMapEntity> tagMapEntities = tagMapRepository.findAllByTagIdAndType(q.getId(),"question");
            // 搜索标签
            List tagList = new LinkedList();
            tagMapEntities.stream().forEach(m -> tagList.add(tagRepository.findById(m.getCorrelation())));
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

}