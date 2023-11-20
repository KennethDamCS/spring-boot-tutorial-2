package com.kennethdamcs.server;

import com.kennethdamcs.server.enumeration.Status;
import com.kennethdamcs.server.model.Server;
import com.kennethdamcs.server.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
}
