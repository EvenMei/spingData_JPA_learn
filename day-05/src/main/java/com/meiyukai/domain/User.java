package com.meiyukai.domain;

import org.hibernate.boot.registry.selector.StrategyRegistration;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_state")
    private String userState;

    //多对多
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 配置 多对多的关系
     *      1.  声明表的关系
     *      2.  配置中间表（包含两个外键）
     *      【主动的一方】设置级联，维护外键
     *      【被动的一方】 放弃外键的维护 mappered by [对方的属性]
     */
    @ManyToMany(targetEntity = Role.class , cascade = CascadeType.ALL)
    @JoinTable(name = "sys_user_role" ,
            //当前对象在中间表的外键
            joinColumns = {@JoinColumn(name = "user_id" , referencedColumnName = "user_id")},
            //对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "role_id" , referencedColumnName = "role_id")}

    )
    Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userCode='" + userCode + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userState='" + userState + '\'' +
                '}';
    }
}
