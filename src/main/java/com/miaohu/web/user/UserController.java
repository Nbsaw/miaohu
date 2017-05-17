package com.miaohu.web.user;

import com.miaohu.domain.quesstion.QuestionEntity;
import com.miaohu.domain.quesstion.QuestionRepository;
import com.miaohu.domain.tag.TagRepository;
import com.miaohu.domain.tag.tagMap.TagMapEntity;
import com.miaohu.domain.tag.tagMap.TagMapRepository;
import com.miaohu.domain.user.UserEntity;
import com.miaohu.domain.user.UserRepository;
import com.miaohu.service.getUserInfo.UserInfoService;
import com.miaohu.service.getUserInfo.UserInfoVO;
import com.miaohu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.miaohu.domain.user.UserType.LOCAL;


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
     * @param session
     * @param phone
     * @param password
     * @return
     */
    // TODO 加密解密,反之中间人攻击
    // TODO 返回账号是否存在
    // TODO 第三方登录
    @PostMapping(value = "/login",produces="application/json;charset=UTF-8")
    public Map login(HttpSession session, @RequestParam("phone") String phone , @RequestParam("password") String password){
        Map result = new LinkedHashMap();
        try {
            UserEntity userEntity = userRepository.login(phone,password);
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setUsername(userEntity.getUsername());
            userInfoVO.setAvatar(userEntity.getAvatar());
            userInfoVO.setSex(userEntity.getSex());
            session.setAttribute("id",userEntity.getId());
            session.setAttribute("oauth_type",LOCAL);
            result.put("code",200);
            result.put("data",userInfoVO);
        }catch (Exception e){
            result.put("code",400);
            result.put("errors","用户名或者密码错误");
        }
        return result;
    }

    // 获取用户信息
    @GetMapping(value = "/info",produces="application/json;charset=UTF-8")
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

    /**
     * 获取用户发表的所有的问题
     * @param session
     * @return 获取用户发表的所有的问题
     */
    // TODO 分页
    @GetMapping(value = "/question", produces="application/json;charset=UTF-8")
    public String question(HttpSession session){
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
            tagMapEntities.stream().forEach(m -> {
                tagList.add(tagRepository.findById(m.getCorrelation()));
            });
            // 把问题加入映射
            map.put("question",q);
            // 添加问题映射
            map.put("tag",tagList);
            // 把映射加到列表
            result.add(map);
        });
        return JsonUtil.formatResult(200,"",result);
    }

    // 账号注销,注销后跳转到首页
    @GetMapping("/logout")
    public void  logout(HttpServletResponse response) throws IOException {
        response.setHeader("Set-Cookie","miaohu=ass;httpOnly=true;path=/;Max-age=-1");
        response.sendRedirect("/");
    }
}
