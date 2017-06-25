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
import com.nbsaw.miaohu.util.JwtUtil;
import com.nbsaw.miaohu.vo.GenericVo;
import com.nbsaw.miaohu.vo.MessageVo;
import com.nbsaw.miaohu.vo.ResultVo;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.message.Message;

import javax.servlet.http.HttpServletRequest;
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

    // jwt
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录接口,判断手机和密码,把信息存在session里面
     * @param phone 手机号码
     * @param password 账号密码
     */
    // TODO 第三方登录
    @PostMapping(value = "/login")
    public GenericVo login(@RequestParam("phone") String phone , @RequestParam("password") String password){
        // TODO 从Redis获取id，再从数据库查信息
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

    /**
     * 获取用户信息
     * @param request 从request的header里面获取token
     * @return 获取用户信息
     */
    @GetMapping(value = "/info")
    public GenericVo information(HttpServletRequest request){
        // TODO 从Redis获取id，再从数据库查信息 -> 主要是判断用户是否注销了
        try {
            // 解析token
            String token = request.getHeader("token");
            System.out.println(token);
            String uid = (String) jwtUtil.parse(token).get("uid");

            // 查询
            UserEntity userEntity = userRepository.findAllById(uid);
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

    // TODO 让token失效
    @PostMapping(value = "/changePassword")
    public MessageVo changePassword(HttpServletRequest request, @RequestParam("password") String password){
        // 返回的数据
        MessageVo messageVo = new MessageVo();

        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

        // 修改密码
        Boolean status = userRepository.updatePasswordByUid(uid,"13164726498") == 1;
        if (status == true){
            messageVo.setCode(200);
            messageVo.setMessage("修改密码成功！");
        }
        else{
            messageVo.setCode(400);
            messageVo.setMessage("修改密码失败！");
        }
        return messageVo;
    }

    /**
     * 获取用户发表的所有的问题
     * @param request 从request的header里面获取token
     * @return 获取用户发表的所有的问题
     */
    // TODO 分页
    @GetMapping(value = "/question")
    public ResultVo question(HttpServletRequest request){
        // 解析token
        String token = request.getHeader("token");
        System.out.println(token);
        String uid = (String) jwtUtil.parse(token).get("uid");

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

    // TODO 资料修改

    // TODO 注销登陆
    // 从redis删除token
}