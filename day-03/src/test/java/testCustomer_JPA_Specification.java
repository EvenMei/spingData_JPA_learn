import com.meiyukai.dao.CustomerDao;
import com.meiyukai.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class testCustomer_JPA_Specification {

    @Resource(name = "customerDao")
    private CustomerDao customerDao;

    /**
     * 简单查询
     */

    @Test
    public void testFindAll(){
        List<Customer> customers =  customerDao.findAll();
        for(Customer customer: customers){
            System.out.println("customer :  "  + customer);
        }
    }


    @Test
    public void testSpec(){

        //匿名内部类
        /**
         * 自定义查询条件
         *          1. 提供范型
         *          2. 实现 toPredicate 方法（构造查询条件）
         *          3. 需要借助方法参数中的两个参数
         *                                                                          root : 获取需要查询的对象属性
         *                                                                          criteriaBuilder : 构造查询条件 内部封装了很多的 查询条件，例如模糊、精准匹配等
         *
         */

        // 动态查询——精准查询
        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 1. 获取比较的属性
                Path<Object> custName = root.get("custName");
                // 2. 构造查询   select * from cst_customer where cust_name like ?
                Predicate predicate= criteriaBuilder.equal(custName, "梅雨凯");
                return predicate;
            }
        };

        Customer customer = customerDao.findOne(specification);
        System.out.println("customer with specification : "  + customer);

    }

    // 动态查询-----多条件查询

    @Test
    public void testMutltiConditions(){
        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1. 获取属性
                Path<Object> custName = root.get("custName");
                Path<Object> custAddress = root.get("custAddress");
                //2. 构造客户的精准匹配查询
                Predicate p1 = criteriaBuilder.equal(custName, "梅雨凯");
                Predicate p2 = criteriaBuilder.equal(custAddress, "上海");
                //3. 将多个查询条件组合在一起 cb.and     cb.or
                Predicate pAnd = criteriaBuilder.and(p1, p2);
//                Predicate pOr = criteriaBuilder.or(p1, p2);

                return pAnd;
            }
        };

        Customer customer = customerDao.findOne(specification);
        System.out.println("customer: "  +customer);
    }

    /**
     * 模糊查询 findAll(Specification<T> specification , Sort sore </>)
     */
    @Test
    public void testFindAllLike(){

        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root , CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                //不能直接拿到比较对象， 用custName调用静态方法as（Class<?>）
                Predicate like = criteriaBuilder.like(custName.as(String.class) , "%meiyukai%" );
                return like ;
            }
        };

       /* List<Customer> customers = customerDao.findAll(specification);
        for(Customer customer: customers){
            System.out.println("customer:  " + customer);
        }*/

       //添加排序
        Sort sort = new Sort(Sort.Direction.DESC , "custAddress");
        List<Customer> customers = customerDao.findAll(specification, sort);
        for(Customer customer : customers){
            System.out.println("customer  : "   + customer);
        }


    }



    /**
     * 分页查询 findAll(Specification<T> specification , Pageable pageable </>)
     */
    @Test
    public void testPageable(){

        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Path<Object> custId = root.get("custId");
                Predicate like = criteriaBuilder.like(custName.as(String.class), "meiyukai");
                Order desc = criteriaBuilder.desc(custId);
                return like;
            }
        };

        Pageable pageable = new PageRequest(0 , 2);

        Page<Customer> page = customerDao.findAll(null, pageable);
        List<Customer> customers = page.getContent();
        for(Customer customer : customers){
            System.out.println("customer  :  "+  customer);
        }

        System.out.println("totalPages :  " + page.getTotalPages());

        System.out.println("totalElements : "  + page.getTotalElements());

    }







}
