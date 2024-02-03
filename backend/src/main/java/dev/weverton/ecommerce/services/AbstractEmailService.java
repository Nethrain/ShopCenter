package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido) {
        SimpleMailMessage smm = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(smm);
    }

    private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido){
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(pedido.getCliente().getEmail());
        smm.setFrom(sender);
        smm.setSubject("Pedido Confirmado #" + pedido.getId());
        smm.setSentDate(new Date(System.currentTimeMillis()));
        smm.setText(pedido.toString());
        return smm;
    }

}
