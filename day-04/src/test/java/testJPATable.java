import com.meiyukai.dao.CustomerDao;
import com.meiyukai.domain.Customer;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class testJPATable {
    @Resource(name = "customerDao")
    private CustomerDao customerDao;



    @Test
    public void test01(){
        List<Customer> customers = customerDao.findAll();
        for(Customer customer : customers){
            System.out.println("customer :  "  + customer);

        }
    }




}
