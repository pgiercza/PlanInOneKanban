package pl.edu.wszib.planinonekanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:db.properties")
public class PlanInOneKanbanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanInOneKanbanApplication.class, args);
    }

}
