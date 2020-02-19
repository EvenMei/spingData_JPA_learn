import com.meiyukai.dao.CustomerDao;
import com.meiyukai.dao.LinkManDao;
import com.meiyukai.domain.Customer;
import com.meiyukai.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:applicationContext.xml")
public class testOneToMany {

    @Resource(name = "linkManDao")
    private LinkManDao linkManDao;

    @Resource(name = "customerDao")
    private CustomerDao customerDao;



    @Test
    public void testFindAll(){
        List<LinkMan> linkMans = linkManDao.findAll();
        for(LinkMan linkMan : linkMans){
            System.out.println("LinkMan : " + linkMan);
        }
    }

    @Test
    public void testSaveLinkMan(){
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("许云飞");
        linkMan.setLkmGender("男");
        linkManDao.save(linkMan);
    }

    @Test
    public void testSaveCustomer(){
        Customer customer = new Customer();
        customer.setCustName("老板");
        customer.setCustAddress("beijing");
        customerDao.save(customer);
    }




    @Test
    @Transactional
    @Rollback(value = false)
    public void testSaveCustomerAndLinMan1(){
        Customer customer = new Customer();
        customer.setCustName("阿里巴巴cc");
        customer.setCustAddress("hangzhou");


        LinkMan  servant = new LinkMan();
        servant.setLkmName("马云cc");
        servant.setLkmPosition("beijing");

        //只关联其中一个关系，关联customer
        customer.getLinkMan().add(servant);
       // servant.setCustomer(customer);

        customerDao.save(customer);

        //linkManDao.save(servant);

    }



    @Test
    @Transactional
    @Rollback(value = false)
    public void testSaveCustomerAndLinMan2(){
        Customer customer = new Customer();
        customer.setCustName("百度");
        customer.setCustAddress("北京");


        LinkMan  ant = new LinkMan();
        ant.setLkmName("蚂蚁金融");
        ant.setLkmPosition("杭州");

        //只关联其中一个关系，关联linkman
        customerDao.save(customer);

        ant.setCustomer(customer);
        linkManDao.save(ant);

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testSaveCustomerAndLinMan3(){
        Customer customer = new Customer();
        customer.setCustName("百度2");
        customer.setCustAddress("北京2");


        LinkMan  ant = new LinkMan();
        ant.setLkmName("蚂蚁金融2");
        ant.setLkmPosition("杭州2");

        //双向关联 , 需要一的一方放弃外键维护
        customer.getLinkMan().add(ant);
        customerDao.save(customer);

        ant.setCustomer(customer);
        linkManDao.save(ant);

    }


    /**
     * 测试级联添加 : 保存一个客户的同时保存所有的联系人
     */

    @Test
    public void testCascadeAdd(){

        Customer customer = new Customer();
        customer.setCustName("级联的客户2");
        customer.setCustAddress("南京s");

        LinkMan linkMan =new LinkMan();
        linkMan.setLkmName("级联的联系人s");
        linkMan.setLkmPosition("锁金村s");

        //关联关系
        customer.getLinkMan().add(linkMan);
        linkMan.setCustomer(customer);

        customerDao.save(customer);


    }






    @Test
    @Transactional
    @Rollback(value = false)
    public void findAllCustomer(){
        List<Customer> customers = customerDao.findAll();
        for(Customer customer : customers ){
            System.out.println("customer : " + customer);
            System.out.println("linkmans  :  " + customer.getLinkMan());
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeDelete(){
        Specification<Customer> specification = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Predicate predicate = criteriaBuilder.like(custName.as(String.class), "级联的客户2");
                return predicate;

            }
        };

        Customer customer = customerDao.findOne(specification);
        customerDao.delete(customer);

    }




}
