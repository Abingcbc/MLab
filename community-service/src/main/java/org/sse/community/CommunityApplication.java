package org.sse.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

/**
 * @author HPY
 */
@SpringBootApplication
@EnableTransactionManagement

public class CommunityApplication {

    public static void main(String[] args) {
        System.out.println(TimeZone.getDefault().getID());
        SpringApplication.run(CommunityApplication.class, args);
    }

}
