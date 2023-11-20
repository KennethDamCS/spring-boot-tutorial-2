package com.kennethdamcs.server.resource;

import com.kennethdamcs.server.enumeration.Status;
import com.kennethdamcs.server.model.Response;
import com.kennethdamcs.server.model.Server;
import com.kennethdamcs.server.service.implementation.ServerServiceImplementation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
    private final ServerServiceImplementation serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.list(30)))
                        .message("Servers retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping ("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.create(server)))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

//@PostMapping("/save")
//public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
//    try {
//        Server createdServer = serverService.create(server);
//
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(now())
//                        .data(Map.of("servers", createdServer))
//                        .message("Server created successfully")
//                        .status(HttpStatus.CREATED)
//                        .statusCode(HttpStatus.CREATED.value())
//                        .build()
//        );
//    } catch (ValidationException ex) {
//        // Handle validation errors and return an appropriate response
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(Response.builder()
//                        .timeStamp(now())
//                        .message("Validation failed")
//                        .status(HttpStatus.BAD_REQUEST)
//                        .statusCode(HttpStatus.BAD_REQUEST.value())
//                        .build());
//    } catch (Exception ex) {
//        // Handle other exceptions and return an appropriate response
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Response.builder()
//                        .timeStamp(now())
//                        .message("Server creation failed")
//                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                        .build());
//    }
//}


    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("servers", serverService.get(id)))
                        .message("Server retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", serverService.delete(id)))
                        .message("Server deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping (path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] deleteServer(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/repos/spring-boot-tutorial-2-images/" +fileName));
    }
}
