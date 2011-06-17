package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AppTest {

    @Test
    public void test() {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.getEnvironment().setActiveProfiles("postgresql");
        ctx.load("classpath:META-INF/spring/app-context.xml");
        ctx.refresh();
        
        DataSource ds = ctx.getBean(DataSource.class);
        String driverClassName = ((BasicDataSource)ds).getDriverClassName();
        assertTrue("The PostgreSQL driver is not being loaded (" + driverClassName + ")", 
                "org.postgresql.Driver".equals(driverClassName));
//        assertTrue("The HSQLDB driver is not being loaded (" + driverClassName + ")", 
//                "org.hsqldb.jdbcDriver".equals(driverClassName));
    }
    
}
