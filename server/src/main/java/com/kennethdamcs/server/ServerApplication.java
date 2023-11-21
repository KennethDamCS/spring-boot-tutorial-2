package com.kennethdamcs.server;

import com.kennethdamcs.server.enumeration.Status;
import com.kennethdamcs.server.model.Server;
import com.kennethdamcs.server.repo.ServerRepo;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.kennethdamcs.server.enumeration.Status.SERVER_DOWN;
import static com.kennethdamcs.server.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepo serverRepo){
        return args -> {
            serverRepo.save(new Server(null, "192.168.0.3", "MacOS", "16GB", "MacBook",
                            "http://localhost:8080/server/image/server1.png", SERVER_DOWN));
            serverRepo.save(new Server(null, "192.168.1.58", "Fedora Linux", "32GB", "Dell Tower",
                    "http://localhost:8080/server/image/server2.png", SERVER_DOWN));
            serverRepo.save(new Server(null, "192.168.1.21", "MS 2008", "64GB", "Web Server",
                    "http://localhost:8080/server/image/server3.png", SERVER_DOWN));
            serverRepo.save(new Server(null, "192.168.1.14", "Red Hat Enterprise Linux", "128GB", "Mail Server",
                    "http://localhost:8080/server/image/server4.png", SERVER_DOWN));
        };
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://localhost:4200");
//        corsConfiguration.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.HEAD.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name()));
//        corsConfiguration.setMaxAge(1800L);
//        source.registerCorsConfiguration("/**", corsConfiguration); // you restrict your path here
//        return (CorsConfigurationSource) source;
//    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
//                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
//        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
//                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Filename"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter();
//    }

}
