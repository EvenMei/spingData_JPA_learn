package com.meiyukai.dao;

import com.meiyukai.domain.Customer;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *   JpaRepository<操作的实体类型 ， 实体类中主键属性的类型></>  封装类基本的 CRUD操作
 *   JpaSpecificationExecutor<操作的实体类型></> 封装了 复杂的查询
 */
@Repository(value = "customerDao")
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query(value = "from Customer where custName= :custName ")   //或者用 ？index代替 , 例如 ?1 例如从第二个参数取值
    public List<Customer> findByName(  @Param(value = "custName") String custName);


    @Query(value = "select count(1) from Customer where custName=:name")
    public  Long getCount(@Param(value = "name") String name);

    @Query(value = "from Customer where custName=:custName and custId=:custId")
    public Customer findByNameAndId(@Param(value = "custName") String custName , @Param(value = "custId") Long custId);

    /**
     * 更新操作
     */

    @Query(value = "update Customer set custName= :custName where custId = :custId")
    @Modifying  //将Query注解 改编为 update / delete
//    @Transactional
    public void updateCustomer(@Param(value = "custName") String custName ,  @Param(value = "custId") Long custId);


    @Query(value = "delete from Customer where custId=:custId")
    @Modifying
    public void deleteCustomer(@Param(value = "custId")  Long custId);

    /**
     * 模糊匹配的 命名规则  (不用写实现 ，写对方法名就可以了)
     *                  1.fingByXXX
     *                  2.findByXXXLike
     *                  3.findByXXXLikeAndYYYY (一个模糊，一个精准 ， 用 and / or  拼接)
     *                  4.findByXXXIn(List<Integer> list )
     * @param custName
     * @return
     */
//    @Query(value = "from Customer where custName like :custName" )
    public List<Customer> findByCustNameLike(/*@Param(value = "custName")*/ String custName);

    public List<Customer> findByCustAddressAndCustNameLike(String custAddress , String custName);

    public List<Customer> findByCustNameLikeAndCustAddress(String custName , String CustAddress);

    //---------------------------------------------------------------- SQL 语句的支持-----------------------------------------------------------------------

    @Query(value = "select  *  from cst_customer" , nativeQuery = true)
    public List<Object[]>findAllObject();


    @Query(value = "select *  from cst_customer" , nativeQuery = true)
    public List<Customer> findCustomerByNativeSQL();


    @Query(value = "delete from cst_customer where cust_id = :custId" , nativeQuery = true)
    @Modifying
    public void deleteCustomerByNativeSql(@Param(value = "custId") Long custId);


    @Query(value = "update cst_customer set cust_Name = :custName where cust_id = :custId " , nativeQuery = true)
    @Modifying
    public void updateByNativeSql(@Param(value = "custName") String custName ,  @Param(value = "custId") Long custId);



}






