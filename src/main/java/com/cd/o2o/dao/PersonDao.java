package com.cd.o2o.dao;

import com.cd.o2o.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao {

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    Person queryPersonByName(String userName);


    /**
     * 添加用户信息
     *
     * @param person
     * @return
     */
    int insertPerson(Person person);


    /**
     * 修改用户信息
     *
     * @param person
     * @return
     */
    int updatePerson(Person person);

}
