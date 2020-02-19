import com.meiyukai.dao.CustomerDao;
import com.meiyukai.domain.Customer;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class testCustomer {
    @Resource(name = "customerDao")
    private CustomerDao customerDao;

    @Test
    public void testFindAll(){
        List<Customer> customers = customerDao.findAll();
        for(Customer customer: customers){
            System.out.println("customer:  " + customer);
        }
    }

    @Test
    public void testFindOne(){
        Customer customer = customerDao.findOne(1L);
        System.out.println("customer:   " + customer);
    }

    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setCustName("小强");
        customerDao.save(customer);
    }


    @Test
    public void testDelete(){
        Customer customer = customerDao.findOne(6L);
        System.out.println("customer:  " +customer);
        customerDao.delete(6L);
    }

    /**
     * 更新 实际也是保存
     */

    @Test
    public void testUpdate(){
        //先查询
        Customer customer= customerDao.findOne(2L);
        //在保存 （id 不可以为空 为空就是保存）
        customer.setCustAddress("上海");
        customerDao.save(customer);
    }


    /**
     * 测试 复杂条件查询
     */

    @Test
    public void testCount(){
        Long count = customerDao.count();
        System.out.println("count :  " +count);
    }

    /**
     * 判断id 为 1 的客户是否存在
     */
    @Test
    public void testExists(){
        boolean exists = customerDao.exists(1L);
        System.out.println("exists  :   " + exists);
    }

    @Test
    @Transactional       // 若要调用 getOne() 方法需要 使用@Transactional 注解
    public void testGetOne(){
        Customer customer = customerDao.getOne(2L);
        System.out.println("customer :  " + customer);
    }

//  ----------------------------------------------------------------JPQL 查询---------------------------------------------------------------------

    @Test
    public void testJPQL(){
        List<Customer> customers = customerDao.findByName("meiyukai");
        for(Customer customer :customers){
            System.out.println("customer    :   " + customer);
        }
    }


    @Test
    public void testJpqlCount(){
        Long count = customerDao.getCount("meiyukai");
        System.out.println("count :  " + count);
    }

    @Test
    public void testFindByIdAndName(){
        Customer customer = customerDao.findByNameAndId("meiyukai"  , 2L);
        System.out.println("customer :  "  +customer);
    }

    @Test
   @Transactional   //在这儿加 transactional 不报错但是数据没有修改，需要到 dao上面加@Transactional 或者继续加 @Rollbalck
    @Rollback(value = false)  // 改变默认回滚的情况
    public void testJpqlUpdate(){
        customerDao.updateCustomer("梅雨凯" , 2L);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteCustomer(){
        customerDao.deleteCustomer(9L);

    }

    @Test
   public void testFindByCustName(){
        List<Customer> customers = customerDao.findByCustNameLike("%mei%");
        for(Customer customer: customers){
            System.out.println("customer:  " + customer);
        }
   }


    /**
     * 测试命名规则
     */
   @Test
   public void testFindByCustAddressAndCustNameLike(){
        List<Customer> customers = customerDao.findByCustAddressAndCustNameLike("上海" , "梅雨凯");
        for(Customer  customer : customers){
            System.out.println("customer :  "  + customer);
        }

   }


   @Test
   public void testFindByCustNameLikeAndCustAddress(){
       List<Customer> customers  = customerDao.findByCustNameLikeAndCustAddress("梅雨凯", "上海");
       for(Customer customer : customers ){
           System.out.println("customer  :  "+ customer);
       }
   }

    //---------------------------------------------------------------- SQL 语句的支持-----------------------------------------------------------------------

    @Test
    public void testFindAllObject(){
        List<Object[]> objects  = customerDao.findAllObject();
        for (Object[] object : objects){
            System.out.println("object :  " + Arrays.toString(object));
        }
    }


    @Test
    public void testNativeSql(){
        List<Customer> customers = customerDao.findCustomerByNativeSQL();
        for(Customer customer : customers){
            System.out.println("customer   :  " + customer);
        }
    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void testDeleteByNativeSql(){
    customerDao.deleteCustomerByNativeSql(9L);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testUpdateByNativeSql(){
        customerDao.updateByNativeSql("苏大强" , 7L);
    }

















}
