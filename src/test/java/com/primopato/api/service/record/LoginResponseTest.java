package com.primopato.api.service.record;

import com.primopato.api.record.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginResponseTest {
    @Test
    void deveCriarLoginResponse() {
        LoginResponse loginResponse = new LoginResponse("token");
        Assertions.assertEquals("token", loginResponse.token());
    }
}
