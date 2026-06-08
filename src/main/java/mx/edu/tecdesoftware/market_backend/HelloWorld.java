package mx.edu.tecdesoftware.market_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//@RequestMapping("/saludar")

public class HelloWorld {
    @GetMapping("/hola")
    public String saludar(){
        return "Hello World";
    }
}
