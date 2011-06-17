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
        assertTrue(ds instanceof BasicDataSource);
    }
    
}
