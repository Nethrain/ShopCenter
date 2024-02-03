package dev.weverton.ecommerce.services;

import dev.weverton.ecommerce.domain.*;
import dev.weverton.ecommerce.domain.enums.ClienteRole;
import dev.weverton.ecommerce.domain.enums.PagamentoStatus;
import dev.weverton.ecommerce.domain.enums.TipoCliente;
import dev.weverton.ecommerce.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public void instantiateTestDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "Bilgi işlem");
        Categoria cat2 = new Categoria(null, "Çalışma masası");
        Categoria cat3 = new Categoria(null, "Yatak masası ve banyo");
        Categoria cat4 = new Categoria(null, "Elektronik");
        Categoria cat5 = new Categoria(null, "Bahçıvanlık");
        Categoria cat6 = new Categoria(null, "Dekorasyon");
        Categoria cat7 = new Categoria(null, "Parfümeri");

        Produto p1 = new Produto(null, "Bilgisayar", 2000.00);
        Produto p2 = new Produto(null, "Yazıcı", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        Produto p4 = new Produto(null, "Çalışma masası", 300.00);
        Produto p5 = new Produto(null, "Havlu", 50.00);
        Produto p6 = new Produto(null, "Yorgan", 200.00);
        Produto p7 = new Produto(null, "Televizyon", 1200.00);
        Produto p8 = new Produto(null, "Makas", 800.00);
        Produto p9 = new Produto(null, "Abajur", 100.00);
        Produto p10 = new Produto(null, "Askılık", 180.00);
        Produto p11 = new Produto(null, "Şampuan", 90.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p5, p6));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
        p4.getCategorias().addAll(Arrays.asList(cat2));
        p5.getCategorias().addAll(Arrays.asList(cat3));
        p6.getCategorias().addAll(Arrays.asList(cat3));
        p7.getCategorias().addAll(Arrays.asList(cat4));
        p8.getCategorias().addAll(Arrays.asList(cat5));
        p9.getCategorias().addAll(Arrays.asList(cat6));
        p10.getCategorias().addAll(Arrays.asList(cat6));
        p11.getCategorias().addAll(Arrays.asList(cat7));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        Estado est1 = new Estado(null, "Ankara");
        Estado est2 = new Estado(null, "İstanbul");

        Cidade c1 = new Cidade(null, "Etimesgut", est1);
        Cidade c2 = new Cidade(null, "Şişli", est2);
        Cidade c3 = new Cidade(null, "Beşiktaş", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Ahmet Özgan", "ahmet@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA, encoder.encode("123"));
        Cliente admin = new Cliente(null, "Yönetici", "yonetici@gmail.com", "04127867531", TipoCliente.PESSOA_FISICA, encoder.encode("123"));
        admin.addRole(ClienteRole.ADMIN);

        cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
        admin.getTelefones().addAll(Arrays.asList("99999999"));

        Endereco e1 = new Endereco(null, "Bahçe Sokak", "15", "Daire 102", "Bahçelievler", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Atatürk Caddesi", "105", "Ofis 800", "Şişli", "38777012", cli1, c2);
        Endereco e3 = new Endereco(null, "İstanbul Caddesi", "105", "Ofis 800", "Şişli", "38777012", admin, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
        admin.getEnderecos().add(e3);

        clienteRepository.saveAll(Arrays.asList(cli1, admin));
        enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoCartao(null, PagamentoStatus.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoBoleto(null, PagamentoStatus.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
