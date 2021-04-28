package com.zyh.forum.mapper;

import com.zyh.forum.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
//User相关操作
@Mapper    //自动继承类并实现方法
public interface UserMapper {
    @Insert("insert into user(name,password,token) values (#{name},#{password},#{token})")//插入操作
    void insert(User user);

    @Select("select * from user where name=#{name} and password=#{password}")
    User select(User user);

    @Select("select  * from user where token=#{token}")
    User findBytoken(String token);//获取token相关方法

    @Select("select * from user where id=#{createid}")
    User findById(int createid);
}

