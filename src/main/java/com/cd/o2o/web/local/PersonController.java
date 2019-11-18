package com.cd.o2o.web.local;

import com.cd.o2o.entity.Person;
import com.cd.o2o.service.PersonService;
import com.cd.o2o.util.HttpServletRequestUtil;
import com.cd.o2o.util.VerifyCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/local")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * 账号注册
     *
     * @param uploadFile
     * @param request
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    private Map<String,Object> register(MultipartFile uploadFile, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //验证码校验
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误!");
            return map;
        }
        //获取前端提交过来的用户名和密码信息
        String personStr = HttpServletRequestUtil.getString(request,"personStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = null;
        try {
            //将personStr字符串转化成Person实体类
            person = objectMapper.readValue(personStr,Person.class);
        } catch (IOException e) {
            map.put("success",false);
            map.put("errorMsg",e.getMessage());
            return map;
        }
        //添加账号信息
        int effectedNum = personService.addPerson(person,uploadFile);
        if(effectedNum <= 0){
            map.put("success",false);
            map.put("errorMsg","账号注册失败！");
            return map;
        }
        map.put("success",true);
        return map;
    }


    /**
     * 登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    private Map<String,Object> login(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取前端提交过来的用户名和密码信息
        String username = HttpServletRequestUtil.getString(request,"username");
        String pwd = HttpServletRequestUtil.getString(request,"pwd");
        Person person = null;
        if(username !=null && pwd !=null){
            person = personService.getPersonByName(username);
            if(person !=null && person.getName().equals(username) && person.getPwd().equals(pwd)){
                request.getSession().setAttribute("person",person);
            }else {
                map.put("success",false);
                map.put("errorMsg","登陆失败！");
                return map;
            }
        }else {
            map.put("success",false);
            map.put("errorMsg","信息不能为空！");
            return map;
        }
        map.put("success",true);
        map.put("person",person);
        return map;
    }


    /**
     * 用户点击登出按钮的时，注销session
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Person person = (Person) request.getSession().getAttribute("person");
        if(person != null){
            request.getSession().setAttribute("person",null);
        }
        map.put("success",true);
        return map;
    }


    /**
     * 登陆
     *
     * @param request
     * @return
     */
    @RequestMapping("/portal")
    @ResponseBody
    private Map<String,Object> portal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Person person = (Person) request.getSession().getAttribute("person");
        int flag = 1; //1: 未登陆 2：未绑定账号 3：进入店家管理系统
        if(person == null){
            flag = 1;
        }else if(person != null && person.getPersonType() == 1){
            flag = 2;
        }else if(person != null && person.getPersonType() == 2){
            flag = 3;
        }
        map.put("success",true);
        map.put("flag",flag);
         return map;
    }


    /**
     * 用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/person_info")
    @ResponseBody
    private Map<String,Object> personInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Person person = (Person) request.getSession().getAttribute("person");
        if(person != null){
            map.put("success",true);
            map.put("person",person);
        }
        return map;
    }
}
