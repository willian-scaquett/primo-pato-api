package com.primopato.api.service;

import com.sendgrid.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedConstruction;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
        setField(emailService, "sendGridApiKey", "fake-key");
        setField(emailService, "fromEmail", "no-reply@primopato.com");
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testEnviarNovaSenha_Success() throws Exception {
        Response mockResponse = new Response();
        mockResponse.setStatusCode(202);

        try (MockedConstruction<SendGrid> mocked = mockConstruction(
                SendGrid.class,
                (mock, context) -> when(mock.api(any(Request.class))).thenReturn(mockResponse)
        )) {
            emailService.enviarNovaSenha("user@example.com", "senha123");

            assertEquals(1, mocked.constructed().size());
            SendGrid constructed = mocked.constructed().getFirst();
            assertNotNull(constructed);

            var captor = ArgumentCaptor.forClass(Request.class);
            verify(constructed).api(captor.capture());
            Request sentRequest = captor.getValue();

            assertEquals(Method.POST, sentRequest.getMethod());
            assertEquals("mail/send", sentRequest.getEndpoint());
            assertTrue(sentRequest.getBody().contains("senha123"));
        }
    }

    @Test
    void testEnviarNovaSenha_FailureWithErrorStatus() throws Exception {
        Response mockResponse = new Response();
        mockResponse.setStatusCode(400);
        mockResponse.setBody("Erro no envio");

        try (MockedConstruction<SendGrid> mocked = mockConstruction(
                SendGrid.class,
                (mock, context) -> when(mock.api(any(Request.class))).thenReturn(mockResponse)
        )) {
            RuntimeException ex = assertThrows(RuntimeException.class, () ->
                    emailService.enviarNovaSenha("user@example.com", "senha123")
            );
            assertTrue(ex.getMessage().contains("Erro ao enviar e-mail"));
            assertTrue(ex.getMessage().contains("Erro no envio"));
        }
    }

    @Test
    void testEnviarNovaSenha_IOException() throws Exception {
        try (MockedConstruction<SendGrid> mocked = mockConstruction(
                SendGrid.class,
                (mock, context) -> when(mock.api(any(Request.class))).thenThrow(new IOException("Falha de rede"))
        )) {
            RuntimeException ex = assertThrows(RuntimeException.class, () ->
                    emailService.enviarNovaSenha("user@example.com", "senha123")
            );
            assertTrue(ex.getMessage().contains("Erro ao enviar e-mail"));
            assertInstanceOf(IOException.class, ex.getCause());
        }
    }
}
