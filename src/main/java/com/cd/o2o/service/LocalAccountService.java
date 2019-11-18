package com.cd.o2o.service;

import com.cd.o2o.entity.LocalAccount;
import org.apache.ibatis.annotations.Param;

public interface LocalAccountService {

    /**
     * 添加平台帐号 / 注册
     *
     * @param localAccount
     * @return
     */
    int addLocalAccount(LocalAccount localAccount);

    /**
     * 通过帐号和密码查询对应信息，供登录用
     *
     * @param username
     * @param password
     * @return
     */
    LocalAccount getLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

}
