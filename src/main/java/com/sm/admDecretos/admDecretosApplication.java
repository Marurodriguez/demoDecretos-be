package com.sm.admDecretos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.TimeZone;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class admDecretosApplication {
    private static final Logger logger = LoggerFactory.getLogger(admDecretosApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(admDecretosApplication.class, args);
        /*logger.info("Versión: " + "2021-07-25");*/
        //      Create un user
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        /*String encodedPassword = passwordEncoder.encode("-");
        logger.info("clave: " + encodedPassword);*/
        TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        System.out.println(TimeZone.getDefault());

        logger.info(">>> INICIO CORRECTAMENTE <<< " + new java.util.Date().toString());
    }
}
