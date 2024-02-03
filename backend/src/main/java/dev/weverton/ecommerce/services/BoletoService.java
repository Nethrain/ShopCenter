package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.PagamentoBoleto;
import dev.weverton.ecommerce.domain.Pedido;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {
    public void setPagamento(PagamentoBoleto pagto, Date instante){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(instante);
        calendario.add(Calendar.DAY_OF_MONTH, 7);

        pagto.setDataVencimento(calendario.getTime());
    }
}
