package com.example.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Table(name = "t_user")
@Data
@ToString
public class User implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private String perms;

    private static final long serialVersionUID = 1L;
}