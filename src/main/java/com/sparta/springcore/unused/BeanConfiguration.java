//package com.sparta.springcore;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class BeanConfiguration {
//    @Bean       // 화살표 방향에 따라 의존의 방향성이 정해짐
//    public ProductRepository productRepository() {
//        String dbId = "sa";
//        String dbPassword = "";
//        String dbUrl = "jdbc:h2:mem:springcoredb";
//        return new ProductRepository(dbId, dbPassword, dbUrl);
//    }
//
//    @Bean
//    @Autowired      // 다른 객체를 꺼내서 사용하기 위해 반드시 필요한 스프링 내장
//    public ProductService productService(ProductRepository productRepository) {
//        return new ProductService(productRepository);
//    }
//}