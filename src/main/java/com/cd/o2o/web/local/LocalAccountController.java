package com.cd.o2o.web.local;

import com.cd.o2o.entity.LocalAccount;
import com.cd.o2o.entity.Person;
import com.cd.o2o.service.LocalAccountService;
import com.cd.o2o.service.PersonService;
import com.cd.o2o.util.HttpServletRequestUtil;
import com.cd.o2o.util.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/local")
public class LocalAccountController {

    @Autowired
    private LocalAccountService localAccountService;
    @Autowired
    private PersonService personService;


    /**
     * 绑定账号
     *
     * @param request
     * @return
     */
    @RequestMapping("/account_bind")
    @ResponseBody //@ResponseBody注解会将这个方法的返回值转换为JSON形式的数据，返回到response中,可以抽象理解成response.getWriter.write(JSON.toJSONString(map));
    private Map<String,Object> addLocalAccount(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String, Object>();
        //验证码校验
        if(!VerifyCodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errorMsg","验证码错误!");
            return map;
        }
        //获取前端提交过来的用户名和密码信息
        String username = HttpServletRequestUtil.getString(request,"username");
        String password = HttpServletRequestUtil.getString(request,"password");

        if(username !=null && password !=null){
            LocalAccount localAccount = new LocalAccount();
            localAccount.setUsername(username);
            localAccount.setPassword(password);
            //从session中获取person信息，绑定到LocalAccount里
            Person person = (Person) request.getSession().getAttribute("person");
            localAccount.setPerson(person);
            //绑定账号
            int effectedNum = localAccountService.addLocalAccount(localAccount);
            if(effectedNum <= 0){
                map.put("success",false);
                map.put("errorMsg","绑定失败！！");
                return map;
            }else {
                if(person.getPersonType() == 1){
                    person.setPersonType(2);
                    int effectNum = personService.modifyPerson(person,null);
                    if(effectNum <= 0){
                        map.put("success",false);
                    }
                }
            }
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errorMsg","用户名和密码不能为空哦！");
            return map;
        }
        return map;
    }


}
