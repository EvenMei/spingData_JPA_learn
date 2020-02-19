import com.meiyukai.dao.CustomerDao;
import com.meiyukai.dao.LinkManDao;
import com.meiyukai.domain.Customer;
import com.meiyukai.domain.LinkMan;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
public class testObjectNavigation {

    @Resource(name = "customerDao")
    private CustomerDao customerDao;


    @Resource(name = "linkManDao")
    private LinkManDao linkManDao;


    @Test
    @Transactional   //解决 nosession 的问题
    @Rollback
    public void testFindCustomerAndLinkMan(){
        Customer customer = customerDao.findOne(3L); // findOne 立即加载 ；getReferences  ，延迟加载

        Set<LinkMan> linkMans = customer.getLinkMan(); // getXXX() 方法默认使用延迟加载 ，
        for(LinkMan linkMan : linkMans){
            System.out.println("linkmMan : "  +  linkMan);
        }

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testFindLinkManAndCustomer(){
        LinkMan linkMan  = linkManDao.findOne(1L);
       // System.out.println("linkMan :  " + linkMan);

        Customer customer = linkMan.getCustomer();   //默认采用即时加载查询出所有的linkMan 和 customer
        System.out.println("customer  :  "  +customer);
    }





}
