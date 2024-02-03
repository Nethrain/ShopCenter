package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.ItemPedido;
import dev.weverton.ecommerce.domain.PagamentoBoleto;
import dev.weverton.ecommerce.domain.Pedido;
import dev.weverton.ecommerce.domain.enums.PagamentoStatus;
import dev.weverton.ecommerce.exceptions.EntityNotFoundException;
import dev.weverton.ecommerce.repositories.ItemPedidoRepository;
import dev.weverton.ecommerce.repositories.PagamentoRepository;
import dev.weverton.ecommerce.repositories.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private ItemPedidoRepository itemPedidoRepository;
    private PagamentoRepository pagamentoRepository;

    private BoletoService boletoService;
    private ProdutoService produtoService;
    private ClienteService clienteService;
    private EmailService emailService;

    public PedidoService(
            PedidoRepository pedidoRepository,
            BoletoService boletoService,
            ProdutoService produtoService,
            ClienteService clienteService,
            EmailService emailService,
            ItemPedidoRepository itemPedidoRepository,
            PagamentoRepository pagamentoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.boletoService = boletoService;
        this.produtoService = produtoService;
        this.itemPedidoRepository = itemPedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.clienteService = clienteService;
        this.emailService = emailService;
    }

    public Pedido find(Long id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(
                () -> new EntityNotFoundException("Pedido(" + id +") não encontrado"));
   }

   @Transactional
   public Pedido store(Pedido pedidoObj){
        pedidoObj.setId(null);
        pedidoObj.setInstante(new Date());
        pedidoObj.getPagamento().setStatus(PagamentoStatus.PENDENTE);
        pedidoObj.getPagamento().setPedido(pedidoObj);
        pedidoObj.setCliente(clienteService.find(pedidoObj.getCliente().getId()));

        if(pedidoObj.getPagamento() instanceof PagamentoBoleto){
            PagamentoBoleto pagto = (PagamentoBoleto) pedidoObj.getPagamento();
            boletoService.setPagamento(pagto, pedidoObj.getInstante());
        }

        pedidoObj = pedidoRepository.save(pedidoObj);
        pagamentoRepository.save(pedidoObj.getPagamento());

        for(ItemPedido item: pedidoObj.getItens()){
            item.setDesconto(0.0);
            item.setProduto(produtoService.find(item.getProduto().getId()));
            item.setValor(item.getProduto().getValor());
            item.setPedido(pedidoObj);
        }

        itemPedidoRepository.saveAll(pedidoObj.getItens());
        emailService.sendOrderConfirmationEmail(pedidoObj);
        
        return pedidoObj;
   }
}
