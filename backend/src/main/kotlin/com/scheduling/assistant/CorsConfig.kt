//package com.scheduling.assistant
//
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.context.annotation.Bean
//import org.springframework.web.servlet.config.annotation.CorsRegistry
//
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
//
//
//
//@SpringBootApplication
//public class RestServiceCorsApplication {
//    @Bean
//    fun CorsConfig(): WebMvcConfigurer? {
//        return object : WebMvcConfigurer {
//            override fun addCorsMappings(registry: CorsRegistry) {
//                registry.addMapping("/suggestions").allowedOrigins("http://localhost:3000")
//            }
//        }
//    }
//}
//
