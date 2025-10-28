package com.primopato.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class PrimoPatoApplicationTest {

    @Test
    void testMain() {
        try (var mocked = mockStatic(SpringApplication.class)) {
            PrimoPatoApplication.main(new String[]{});
            mocked.verify(() -> SpringApplication.run(PrimoPatoApplication.class, new String[]{}));
        }
    }
}
