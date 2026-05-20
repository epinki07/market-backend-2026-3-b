package mx.edu.tecdesoftware.market_backend_2026_3_b;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/saludar")
    public String helloWorld(){
        return "Hello World!";
    }
}