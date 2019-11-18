package com.cd.o2o.service;

import com.cd.o2o.entity.Person;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {

    /**
     * 添加用户信息
     *
     * @param person
     * @return
     */
    int addPerson(Person person, MultipartFile multipartFile);


    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    Person getPersonByName(String userName);


    /**
     * 修改用户信息
     *
     * @param person
     * @return
     */
    int modifyPerson(Person person, MultipartFile multipartFile);

}
