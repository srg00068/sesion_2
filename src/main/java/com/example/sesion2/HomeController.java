package com.example.sesion2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {

@GetMapping("/")
    public String MetodoGetHTML() {   
        return "home";
    }

@GetMapping("/login")
public String getMethodLogin() {
    return "login";
}

@GetMapping("/register")
public String getMethodRegister() {
    return "register";
}




}
