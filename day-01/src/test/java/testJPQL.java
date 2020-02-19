import com.meiyukai.domain.Customer;
import com.meiyukai.uitils.JPAUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class testJPQL {
    /**
     * 使用JPQL 语句进行
     *                              简单查询
     *                              分页
     *                              排序
     *                              聚合函数
     *
     */

    /**
     * 查询全部
     */
    @Test
    public void testFindAll(){
        EntityManager entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Query query = entityManager.createQuery(" from Customer");
        List<Customer> list = query.getResultList();
        for(Customer customer : list){
            System.out.println("customer:   " + customer );
        }

        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }


    /**
     * 排序查询（倒序查询所有客户 id 倒序v）
     *
     * select *  from cst_customer order  by cust_id;
     * from Customer order by id
     */
    @Test
    public void testOrder(){
        EntityManager entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Query query = entityManager.createQuery("from Customer order by cust_id desc");
        List<Customer> customers = query.getResultList();
        for(Customer customer : customers){
            System.out.println("customer :  " + customer);
        }

        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }


    /**
     * 统计函数 count
     *
     */
    @Test
    public void testCount(){
        EntityManager entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Query query = entityManager.createQuery("select count (custId) from Customer ");
        Object count = query.getSingleResult();
        System.out.println("count :  "+ count);


        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }


    /**
     * 测试分页查询
     */
    @Test
    public void testPageDistributed(){
        EntityManager entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Query query = entityManager.createQuery("from Customer");
        query.setFirstResult(1);   //下标从 0开始
        query.setMaxResults(1); // 开始后 查询几个 ？
        List<Customer> list = query.getResultList();
        for(Customer customer : list){
            System.out.println("customer  :  " + customer);

        }


        transaction.commit();
        entityManager.close();
        JPAUtils.release();


    }


    /**
     * 测试条件查询
     */
    @Test
    public void testCondition(){

        EntityManager entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query  = entityManager.createQuery("from Customer where custName like :name");
        query.setParameter("name" , "%mei%");
        List<Customer> customers = query.getResultList();
        for(Customer customer: customers){
            System.out.println("customer:   " +customer);
        }

        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }


    /**
     * 测试 聚合函数 MAX MIN SUM AVG
     *       max(对象的属性)
     *       sum(对象的属性)
     *      avg(对象的属性)
     *      min(对象的属性)
     */
    @Test
    public void testConverge(){

        EntityManager entityManager = JPAUtils.getEntityManger();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Query query = entityManager.createQuery("select max (c.custId) from Customer  c");
        Object result = query.getSingleResult();

        System.out.println("result :  " + result);

        transaction.commit();
        entityManager.close();
        JPAUtils.release();

    }





}
