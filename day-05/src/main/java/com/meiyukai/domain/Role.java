package com.meiyukai.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_memo")
    private String roleMemo;

    //多对多

    //@ManyToMany(targetEntity = User.class)
    /*@JoinTable(name = "sys_user_role"  ,
            joinColumns = {@JoinColumn(name = "role_id" , referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id" , referencedColumnName = "user_id")}
    )*/

    /**
     * 对多对
     *              【主动的一方】设置级联 、维护外键
     *              【被动的一方】放弃外键的维护、mappered by [对方的属性]
     */
    @ManyToMany(mappedBy = "roles" ) // " role" 为对方的属性
   Set<User> users = new HashSet<User>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUses(Set<User> users) {
        this.users = users;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleMemo() {
        return roleMemo;
    }

    public void setRoleMemo(String roleMemo) {
        this.roleMemo = roleMemo;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleMemo='" + roleMemo + '\'' +
                '}';
    }
}
