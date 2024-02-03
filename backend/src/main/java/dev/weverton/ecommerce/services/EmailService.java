package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendOrderConfirmationEmail(Pedido pedido);
    void sendEmail(SimpleMailMessage msg);
}
