package com.cd.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/local")
public class LocalController {

    /**
     * 帐号注册页路由
     *
     * @return
     */
    @RequestMapping(value = "/to_register")
    private String toRegister() {
        return "local/register";
    }


    /**
     * 登陆页路由
     *
     * @return
     */
    @RequestMapping(value = "/to_login")
    private String toLogin() {
        return "local/login";
    }


    /**
     * 登陆页路由
     *
     * @return
     */
    @RequestMapping(value = "/to_account_bind")
    private String toAccountBind() {
        return "local/account_bind";
    }


}
