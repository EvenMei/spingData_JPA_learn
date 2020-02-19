import com.meiyukai.dao.RoleDao;
import com.meiyukai.dao.UserDao;
import com.meiyukai.domain.Role;
import com.meiyukai.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class testManyToMany {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "roleDao")
    private RoleDao roleDao;


    @Test
    public void testFinAllRoles (){
        List<Role> roles = roleDao.findAll();
        for(Role role : roles  ) {
            System.out.println("role : " + role);
        }

    }

    @Test
    public void testFindAllUsers(){
        List<User> users = userDao.findAll();
        for(User user: users){
            System.out.println("user  : "  + user);
        }
    }


    /**
     * 放弃外键维护  ：主动的一方维护 ， 被动的一方放弃外键的维护
     */

    @Test
    @Transactional
    @Rollback(value = false)
    public void testSaveRolesAndCustomer(){

        Role role = new Role();
        role.setRoleName("学生");

        User user = new User();
        user.setUserName("老板");

       role.getUsers().add(user);
        user.getRoles().add(role);

        roleDao.save(role);
        userDao.save(user);

    }



    /**
     * 测试级联保存
     */

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeSave(){

        Role role = new Role();
        role.setRoleName("码农3");


        User user = new User();
        user.setUserName("主管3");

        role.getUsers().add(user);
        user.getRoles().add(role);

//        roleDao.save(role);
        userDao.save(user);  //cascade 交给 user(主动的一方)

    }


    /**
     * 级联删除 只能删除 有cascade  修饰的entity ，
     * 对于 被中间表关联的entity 或报错
     *
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeDeleteRole(){
        Specification<Role> specification = new Specification<Role>() {
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> roleName = root.get("roleName");
                Predicate predicate = criteriaBuilder.like(roleName.as(String.class), "码农");
                return predicate;
            }
        };

        List<Role> roles = roleDao.findAll(specification);

        for(Role r: roles){
            System.out.println("role:  "+   r);
//            roleDao.delete(r);  // 级联删除失败，被外键关联

        }


    }




    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeDeleteUser(){
        Specification<User> specification  = new Specification<User>() {
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> userName = root.get("userName");
                Predicate predicate = criteriaBuilder.like(userName.as(String.class), "主管");

                return predicate;

            }
        } ;



        List<User> users= userDao.findAll(specification);
        for (User user:  users){
            System.out.println("user:  " + user);
            userDao.delete(user);
        }
    }







}



