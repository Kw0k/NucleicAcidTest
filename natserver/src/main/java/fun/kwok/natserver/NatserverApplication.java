package fun.kwok.natserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
public class NatserverApplication implements CommandLineRunner {


    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(NatserverApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("数据源连接成功：" + dataSource.getConnection());
    }

}
