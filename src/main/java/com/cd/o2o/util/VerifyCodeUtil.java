package com.cd.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class VerifyCodeUtil {

    public static boolean checkVerifyCode(HttpServletRequest request){
        //获取生产的验证码
        String verifyCodeGenerated = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //获取实际提交的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request,"verifyCode");
        //判断实际提交的验证码是否与生产的验证码是否一致
        if(verifyCodeActual == null && !verifyCodeActual.equals(verifyCodeGenerated)){
            return false;
        }
        return true;
    }
}
