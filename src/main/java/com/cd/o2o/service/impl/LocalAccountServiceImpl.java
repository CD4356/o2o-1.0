package com.cd.o2o.service.impl;

import com.cd.o2o.dao.LocalAccountDao;
import com.cd.o2o.entity.LocalAccount;
import com.cd.o2o.service.LocalAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("localAccountService")
public class LocalAccountServiceImpl implements LocalAccountService {

    @Autowired
    private LocalAccountDao localAccountDao;

    public int addLocalAccount(LocalAccount localAccount) {
        localAccount.setCreateTime(new Date());
        localAccount.setLastEditTime(new Date());
        int effectedNum = localAccountDao.insertLocalAccount(localAccount);
        if(effectedNum <= 0){
            throw new RuntimeException("注册失败！");
        }
        return effectedNum;
    }

    public LocalAccount getLocalByUserNameAndPwd(String username, String password) {
        return null;
    }
}
