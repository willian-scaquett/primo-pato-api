package com.primopato.api.service.record;

import com.primopato.api.record.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginRequestTest {
    @Test
    void deveCriarLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("admin", "admin");
        Assertions.assertEquals("admin", loginRequest.usuario());
        Assertions.assertEquals("admin", loginRequest.senha());
    }
}
