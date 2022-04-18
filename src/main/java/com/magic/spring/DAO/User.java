package com.magic.spring.DAO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TheHs
 * @Description User POJO
 */
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String  password;
    private String salt;
    private String phone;
    private String  email;
    private Integer gender;
    private String avatar;
    private Integer isDeleted = 0;
}
