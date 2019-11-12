package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class WelcomeController {
    public WelcomeController(@Value("${welcome.message}") String sVal) {
        this.sVal = sVal;
    }

    public String sVal ;


    @GetMapping("/env")
    public String sayHello() {
        sVal = "Hello world";
        return sVal;
    }
}