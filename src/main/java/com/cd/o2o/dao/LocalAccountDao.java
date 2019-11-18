package com.cd.o2o.dao;

import com.cd.o2o.entity.LocalAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalAccountDao {

    /**
     * 添加平台帐号 / 注册
     *
     * @param localAccount
     * @return
     */
    int insertLocalAccount(LocalAccount localAccount);


    /**
     * 通过帐号和密码查询对应信息，供登录用
     *
     * @param username
     * @param password
     * @return
     */
    LocalAccount queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

}
