package com.usic.usic.model.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IEnviarEmail {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(IEnviarEmail.class);

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
            logger.info("Preparando correo para enviar a: {}", to);

            // Crear el mensaje
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            // Configurar el mensaje
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, esHtml);

            // Enviar el mensaje
            mailSender.send(mensaje);
            logger.info("Correo enviado exitosamente a: {}", to);

        } catch (MessagingException e) {
            logger.error("Error al enviar el correo a {}: {}", to, e.getMessage());
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }
}
