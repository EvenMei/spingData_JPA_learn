import com.meiyukai.domain.Customer;
import com.meiyukai.uitils.JPAUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class testJPA {
    /**
     * 测试 JPA的保存
     * //1. 加载配置文件创建工厂，（实体管理类工厂）对象
     *         //2. 通过实体类管理工厂获取实体管理器
     *         //3. 获取事务对象开启事务
     *         // 4. 完成 CRUD
     *         // 5.  提交事务
     *         //6. 释放资源
     *
     */


    /**
     * 测试保存
     */
    @Test
    public void testSave(){
       /* //1. 加载配置文件创建工厂，（实体管理类工厂）对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        //2. 通过实体类管理工厂获取实体管理器
        EntityManager entityManager = factory.createEntityManager();
        //3. 获取事务对象开启事务
        EntityTransaction transaction = entityManager.getTransaction(); // 获取事务对象*/
       EntityManager entityManager = JPAUtils.getEntityManger();
       EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 完成 CRUD
        Customer customer = new Customer();
        customer.setCustName("meiyukai");
        customer.setCustAddress("nanjing/suojingcun");
        customer.setCustIndustry("none");
        customer.setCustLevel("IT");
        customer.setCustPhone("13323243242");
        customer.setCustSource("adds");
        entityManager.persist(customer); //保存用户  {merge(更新) / remove(删除) / find(getReference) 根据ID 查询// }
        // 5.  提交事务
        transaction.commit();
        //6. 释放资源
        entityManager.close();
        JPAUtils.release();

    }

    /**
     * 测试查询(find 方法)
     *                                即时加载
     *                                  得到的是真实对象
     *
     */
    @Test
    public void testFind(){
        EntityManager  entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class , 1L);
        System.out.println("customer :   " + customer);
        transaction.commit();
        entityManager.close();
        JPAUtils.release();
    }


    /**
     * 测试查询(getReference 方法)
     *                                                  懒加载
     *                                                  获取的是引用对象
     */
    @Test
    public void testReference(){
        EntityManager  entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.getReference(Customer.class , 1L);
        System.out.println("customer :   " + customer);
        transaction.commit();
        entityManager.close();
        JPAUtils.release();
    }






    /**
     * 测试 更新
     */

    @Test
    public void testMerge(){
        EntityManager  entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Customer customer = entityManager.find(Customer.class , 1L);
        customer.setCustName("更新名字");
        entityManager.merge(customer);
        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }

    /**
     * 测试删除 客户
     */
    @Test
    public void testRemove(){
        EntityManager  entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer =  entityManager.getReference(Customer.class , 3L);
        entityManager.remove(customer);

        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }




}
