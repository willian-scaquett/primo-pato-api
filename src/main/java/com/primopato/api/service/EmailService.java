package com.primopato.api.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${spring.mail.from}")
    private String fromEmail;

    public void enviarNovaSenha(String destinatario, String novaSenha) {
        Email from = new Email(fromEmail);
        Email to = new Email(destinatario);
        String subject = "Recuperação de Senha - Primo Pato";
        Content content = new Content("text/plain",
                "Olá,\n\n" +
                        "Você solicitou a recuperação de senha.\n\n" +
                        "Sua nova senha é: " + novaSenha + "\n\n" +
                        "Por favor, altere sua senha após o primeiro login.\n\n" +
                        "Se você não solicitou esta alteração, entre em contato conosco imediatamente.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe Primo Pato"
        );

        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("Erro ao enviar e-mail: " + response.getBody());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao enviar e-mail", ex);
        }
    }
}

