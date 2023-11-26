package com.example.adlogin.ldap;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;

}
