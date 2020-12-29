package de.froesche.nz.rest;

import jakarta.ws.rs.Path;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.*;
import java.io.IOException;

@RestController()
public class PersonResource {

    @GetMapping("/test")
    String all() {
        return "This is a Test";
    }
}
