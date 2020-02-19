package com.meiyukai.uitils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 解决 EntityManager 耗时 和 浪费资源的问题
 */
public class JPAUtils {
    //只加载一次
    private static EntityManagerFactory factory=null;

    static{
        factory  =  Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 用来获取EntityManager 对象
     */

    public static EntityManager getEntityManger(){
        return factory.createEntityManager();
    }

    /**
     *释放资源
     */

    public static void release(){
        factory.close();
    }


}
