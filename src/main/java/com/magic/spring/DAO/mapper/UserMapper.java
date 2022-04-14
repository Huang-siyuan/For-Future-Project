package com.magic.spring.DAO.mapper;

import com.magic.spring.DAO.POJO.User;
import org.apache.ibatis.annotations.*;

/**
 * @author Hsy
 * @Description The DAO interface of User
 */
public interface UserMapper {

    /**
     * @param user
     * @return the number of rows affected
     * @Description The method to insert a new user
     * @Description We can directly use the user obj as a parameter to insert a new user.
     * User attributes can be directly mapped into SQL statements.
     * Make sure the parameter name in the placeholder matches the member parameter in the POJO.
     * For example, we should use the "createdUser" in the placeholder instead of "created_user".
     * The @Options annotation is used to specify the key generator.
     * The new user's id will be automatically generated by the key generator. And will be set in the user object;
     */
    @Insert("insert into t_user(username, password, salt, phone, email, gender, avatar, created_user, created_time, modified_user, modified_time) " +
            "values (#{username},#{password}, #{salt}, #{phone}, #{email}, #{gender}, #{avatar}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    Integer insertUser(User user);

    /**
     * @param username
     * @return the user obj
     * @Description The method to get a user by username.
     *              The @Results annotation is used to specify the result map.
     *              Property is the field name in POJO and column is the column name in the table.
     */
    @Select("select * from t_user where username=#{username}")
    @Results(id = "userMap", value = {
            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time"),
            @Result(property = "isDeleted", column = "is_deleted")
    })
    User findUserByUsername(String username);

    /**
     * @param uid
     * @return the user obj
     * @Description The method to get a user by uid.
     *              And we can directly use "userMap" as the result map.
     *              It can load the result map from the previous method(findUserByUsername).
     */
    @Select("select * from t_user where uid=#{uid}")
    @ResultMap("userMap")
    User findUserByUid(Integer uid);
}