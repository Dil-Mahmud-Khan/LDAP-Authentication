package com.example.adlogin.junk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "You have successfully logged in Using Spring Security LDAP Authentication!";
    }
}