package com.usic.usic.model.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class IEnviarEmail {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo con soporte para HTML o texto plano.
     *
     * @param to      Dirección del destinatario
     * @param subject Asunto del correo
     * @param body    Cuerpo del correo (puede ser HTML)
     * @param esHtml  Indica si el cuerpo del correo es HTML
     */
    public void enviarCorreo(String to, String subject, String body, boolean esHtml) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, esHtml); // Define si es HTML

            mailSender.send(mensaje); // Enviar el mensaje
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}

