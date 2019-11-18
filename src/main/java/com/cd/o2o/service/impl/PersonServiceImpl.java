package com.cd.o2o.service.impl;

import com.cd.o2o.dao.PersonDao;
import com.cd.o2o.entity.Person;
import com.cd.o2o.service.PersonService;
import com.cd.o2o.util.ImageUtil;
import com.cd.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    /**
     * 账号注册
     *
     * @param person
     * @param multipartFile
     * @return
     */
    @Transactional
    public int addPerson(Person person, MultipartFile multipartFile) {
        //给账号信息赋初始值
        person.setCreateTime(new Date());
        person.setLastEditTime(new Date());
        person.setEnableStatus(1);
        person.setPersonType(1);
        try{
            //添加图片
            addLocalImg(person,multipartFile);
        }catch (Exception e){
            throw new RuntimeException("addLocalImg error:" + e.getMessage());
        }
        //添加账号
        int effectedNum = personDao.insertPerson(person);
        if(effectedNum <= 0){
            throw new RuntimeException("账号注册失败！");
        }
        return effectedNum;
    }


    /**
     * 添加不带水印的缩略图，并将图片保存的相对路径设置到Person对象的ProfileImg属性里
     *
     * @param person
     * @param multipartFile
     */
    private void addLocalImg(Person person, MultipartFile multipartFile){
        // 定义用户头像的相对路径
        String relativeAddress = PathUtil.getLocalImagePath(person.getName());
        // 生成不带水印的缩略图，并返回其相对路径
        String localImgAddress = ImageUtil.thumbnail(multipartFile,relativeAddress);
        // 将相对地址设置进Person实体类的属性中
        person.setProfileImg(localImgAddress);
    }


    /**
     * 根据用户名获取用户信息，用于登陆
     *
     * @param userName
     * @return
     */
    public Person getPersonByName(String userName) {
        return personDao.queryPersonByName(userName);
    }


    /**
     * 修改用户信息
     *
     * @param person
     * @param multipartFile
     * @return
     */
    @Transactional
    public int modifyPerson(Person person, MultipartFile multipartFile) {
        int effectNum = 0;
        //空值判断
        if(person != null){
            if(multipartFile != null){
                try{
                    //删除原有的图片
                    ImageUtil.deleteFileOrPath(person.getProfileImg());
                }catch (Exception e){
                    throw  new RuntimeException(e.getMessage());
                }
                //添加新图片
                addLocalImg(person,multipartFile);
            }
            //修改用户信息
           effectNum = personDao.updatePerson(person);
        }
        return effectNum;
    }


}
