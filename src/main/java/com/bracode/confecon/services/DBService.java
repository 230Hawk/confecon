package com.bracode.confecon.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bracode.confecon.domain.Cidade;
import com.bracode.confecon.domain.Cliente;
import com.bracode.confecon.domain.Endereco;
import com.bracode.confecon.domain.EnderecoUsuario;
import com.bracode.confecon.domain.Estado;
import com.bracode.confecon.domain.Grupo;
import com.bracode.confecon.domain.ItemPedido;
import com.bracode.confecon.domain.Marca;
import com.bracode.confecon.domain.Pagamento;
import com.bracode.confecon.domain.PagamentoBoleto;
import com.bracode.confecon.domain.PagamentoCartao;
import com.bracode.confecon.domain.Pedido;
import com.bracode.confecon.domain.Produto;
import com.bracode.confecon.domain.Situacao;
import com.bracode.confecon.domain.Usuario;
import com.bracode.confecon.domain.enums.EstadoPagamento;
import com.bracode.confecon.domain.enums.Perfil;
import com.bracode.confecon.domain.enums.TipoCliente;
import com.bracode.confecon.domain.enums.TipoUser;
import com.bracode.confecon.repositories.CidadeRepository;
import com.bracode.confecon.repositories.ClienteRepository;
import com.bracode.confecon.repositories.EnderecoRepository;
import com.bracode.confecon.repositories.EstadoRepository;
import com.bracode.confecon.repositories.GrupoRepository;
import com.bracode.confecon.repositories.ItemPedidoRepository;
import com.bracode.confecon.repositories.MarcaRepository;
import com.bracode.confecon.repositories.PagamentoRepository;
import com.bracode.confecon.repositories.PedidoRepository;
import com.bracode.confecon.repositories.ProdutoRepository;
import com.bracode.confecon.repositories.SituacaoRepository;
import com.bracode.confecon.repositories.UsuarioRepository;


@Service
public class DBService {
	
	

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private SituacaoRepository situacaoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	
	public void instantiateTestDataBase() throws ParseException {
		
		
		Produto p1 = new Produto(null, "111", "Amortecedor", "Amortecedor 1", "Gol G5", 10.00, 100.00);
		Produto p2 = new Produto(null, "222", "Mola", "Mola 2", "Gol G5", 20.00, 200.00);
		Produto p3 = new Produto(null, "333", "Balança", "Balança 3", "Gol G5", 30.00, 300.00);
		Produto p4 = new Produto(null, "444", "Coxim", "Coxim 4", "Gol G5", 40.00, 400.00);
		Produto p5 = new Produto(null, "555", "Barra", "Barra 5", "Gol G5", 50.00, 500.00);
		Produto p6 = new Produto(null, "666", "Rolamento", "Rolamento 6", "Gol G5", 60.00, 600.00);
		Produto p7 = new Produto(null, "777", "Capo", "Capo 7", "Gol G5", 70.00, 700.00);
		Produto p8 = new Produto(null, "888", "Porta", "Porta 8", "Gol G5", 80.00, 800.00);
		Produto p9 = new Produto(null, "999", "Paralama", "Paralama 9", "Gol G5", 90.00, 900.00);
		Produto p10 = new Produto(null, "101010", "Parachoque", "Parachoque 10", "Gol G5", 100.00, 1000.00);
		Produto p11 = new Produto(null, "111111", "Tampa", "Tampa 11", "Gol G5", 110.00, 1100.00);
		Produto p12 = new Produto(null, "121212", "Teto", "Teto 12", "Gol G5", 120.00, 1200.00);
		Produto p13 = new Produto(null, "111", "Amortecedor", "Amortecedor 1", "Gol G6", 10.00, 100.00);
		Produto p14 = new Produto(null, "222", "Mola", "Mola 2", "Gol G6", 20.00, 200.00);
		Produto p15 = new Produto(null, "333", "Balança", "Balança 3", "Gol G6", 30.00, 300.00);
		Produto p16 = new Produto(null, "444", "Coxim", "Coxim 4", "Gol G6", 40.00, 400.00);
		Produto p17 = new Produto(null, "555", "Barra", "Barra 5", "Gol G6", 50.00, 500.00);
		Produto p18 = new Produto(null, "666", "Rolamento", "Rolamento 6", "Gol G6", 60.00, 600.00);
		Produto p19 = new Produto(null, "777", "Capo", "Capo 7", "Gol G6", 70.00, 700.00);
		Produto p20 = new Produto(null, "888", "Porta", "Porta 8", "Gol G6", 80.00, 800.00);
		Produto p21 = new Produto(null, "999", "Paralama", "Paralama 9", "Gol G6", 90.00, 900.00);
		Produto p22 = new Produto(null, "101010", "Parachoque", "Parachoque 10", "Gol G6", 100.00, 1000.00);
		Produto p23 = new Produto(null, "111111", "Tampa", "Tampa 11", "Gol G6", 110.00, 1100.00);
		Produto p24 = new Produto(null, "121212", "Teto", "Teto 12", "Gol G6", 120.00, 1200.00);
		
		
		Grupo  grupo1 = new Grupo(null, "Suspenção");
		Grupo  grupo2 = new Grupo(null, "Lataria");
		Grupo  grupo3 = new Grupo(null, "Injeção");
		Grupo  grupo4 = new Grupo(null, "Acessórios");
		Grupo  grupo5 = new Grupo(null, "Pneus");
		Grupo  grupo6 = new Grupo(null, "Motor");
		Grupo  grupo7 = new Grupo(null, "Sensores");
		Grupo  grupo8 = new Grupo(null, "Sondas");
		Grupo  grupo9 = new Grupo(null, "Exaustão");
		Grupo  grupo10 = new Grupo(null, "Molas");
		Grupo  grupo11 = new Grupo(null, "Turbo");
		Grupo  grupo12 = new Grupo(null, "ECU");
		
		Marca marca1 = new Marca(null, "Ford");
		Marca marca2 = new Marca(null, "GM");
		
		Situacao situacao1 = new Situacao(null, "Nova");
		Situacao situacao2 = new Situacao(null, "Usada");
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Goias");
	
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Goiânia", est2);
		Cidade c3 = new Cidade(null, "Anapolis", est2);
		
		Cliente cli1 = new Cliente(null,"Leo Falcão","Leo LTDA", "leonardo@gmail.com", "10795211000111", "152092370111", TipoCliente.PESSOA_JURIDICA, "Marcelo");
		Cliente cli2 = new Cliente(null,"Lula Silva", "Lula SA", "lula@gmail.com", "25206882000177", "152038695110", TipoCliente.PESSOA_JURIDICA, "Joao");
		Cliente cli3 = new Cliente(null,"Leo Hawk", "Hawk LTDA", "leonardofalcaog@gmail.com", "04985151000105", "15607303111", TipoCliente.PESSOA_JURIDICA, "Joze");
		
		Usuario user1 = new Usuario(null,"ULeo Falcão", "leonardo@gmail.com", pe.encode("123"), TipoUser.ADMIN, "27660672134");
		Usuario user2 = new Usuario(null,"ULula Silva", "lula@gmail.com", pe.encode("123"), TipoUser.AUXILIAR, "89168070187");
		
		
		Endereco e1 = new Endereco(null, "C-185", "sn", "q 609 l 10", "nova suiça", "74280110", cli1, c1);
		Endereco e2 = new Endereco(null, "C-180", "sn", "q 459 l 3", "Jardim America", "74275180", cli1, c1);
		Endereco e3 = new Endereco(null, "C-171", "sn", "q 205 l 5", "Jardim America", "74747222", cli2, c2);
		Endereco e4 = new Endereco(null, "C-255", "sn", "q 275 l 12", "Parque Amazonia", "74255321", cli2, c3);
		Endereco e5 = new Endereco(null, "C-100", "sn", "q 20 l 2", "Campinas", "74255280", cli3, c3);
		
		EnderecoUsuario eu1 = new EnderecoUsuario(null, "C-185", "sn", "q 609 l 10", "nova suiça", "74280110", user1, c1);
		EnderecoUsuario eu2 = new EnderecoUsuario(null, "C-180", "sn", "q 459 l 3", "Jardim America", "74275180", user1, c1);
		EnderecoUsuario eu3 = new EnderecoUsuario(null, "C-171", "sn", "q 205 l 5", "Jardim America", "74747222", user2, c2);
		EnderecoUsuario eu4 = new EnderecoUsuario(null, "C-255", "sn", "q 275 l 12", "Parque Amazonia", "74255321", user2, c3);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse(" 10/06/2017 12:30 "), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse(" 20/08/2017 14:30 "), cli1, e2);
		
		Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("28/08/2018 00:00"), null );
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 10.00, 100.00);
		ItemPedido ip2 = new ItemPedido(ped1, p2, 0.00, 2, 20.00, 200.00);
		ItemPedido ip3 = new ItemPedido(ped2, p3, 0.00, 3, 30.00, 300.00);
		
		grupo1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p13, p14, p15, p16, p17, p18));
		grupo2.getProdutos().addAll(Arrays.asList(p7, p8, p9, p10, p11, p12, p19, p20, p21, p22, p23, p24));
		
		marca1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p13, p14, p15, p16, p17, p18));
		marca2.getProdutos().addAll(Arrays.asList(p7, p8, p9, p10, p11, p12, p19, p20, p21, p22, p23, p24));
		
		situacao1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p13, p14, p15, p16, p17, p18));
		situacao2.getProdutos().addAll(Arrays.asList(p7, p8, p9, p10, p11, p12, p19, p20, p21, p22, p23, p24));
				
		p1.getGrupos().addAll(Arrays.asList(grupo1, grupo7));
		p2.getGrupos().addAll(Arrays.asList(grupo1));
		p3.getGrupos().addAll(Arrays.asList(grupo1));
		p4.getGrupos().addAll(Arrays.asList(grupo1));
		p5.getGrupos().addAll(Arrays.asList(grupo1));
		p6.getGrupos().addAll(Arrays.asList(grupo1));
		p13.getGrupos().addAll(Arrays.asList(grupo1, grupo7));
		p14.getGrupos().addAll(Arrays.asList(grupo1));
		p15.getGrupos().addAll(Arrays.asList(grupo1));
		p16.getGrupos().addAll(Arrays.asList(grupo1));
		p17.getGrupos().addAll(Arrays.asList(grupo1));
		p18.getGrupos().addAll(Arrays.asList(grupo1));
		
		p7.getGrupos().addAll(Arrays.asList(grupo2));
		p8.getGrupos().addAll(Arrays.asList(grupo2));
		p9.getGrupos().addAll(Arrays.asList(grupo2));
		p10.getGrupos().addAll(Arrays.asList(grupo2));
		p11.getGrupos().addAll(Arrays.asList(grupo2));
		p12.getGrupos().addAll(Arrays.asList(grupo2));
		p19.getGrupos().addAll(Arrays.asList(grupo2));
		p20.getGrupos().addAll(Arrays.asList(grupo2));
		p21.getGrupos().addAll(Arrays.asList(grupo2));
		p22.getGrupos().addAll(Arrays.asList(grupo2));
		p23.getGrupos().addAll(Arrays.asList(grupo2));
		p24.getGrupos().addAll(Arrays.asList(grupo2));
		
		p1.getMarcas().addAll(Arrays.asList(marca1, marca2));
		p2.getMarcas().addAll(Arrays.asList(marca1));
		p3.getMarcas().addAll(Arrays.asList(marca1, marca2));
		p4.getMarcas().addAll(Arrays.asList(marca1, marca2));
		p5.getMarcas().addAll(Arrays.asList(marca1));
		p6.getMarcas().addAll(Arrays.asList(marca1));
		p13.getMarcas().addAll(Arrays.asList(marca1, marca2));
		p14.getMarcas().addAll(Arrays.asList(marca1));
		p15.getMarcas().addAll(Arrays.asList(marca1, marca2));
		p16.getMarcas().addAll(Arrays.asList(marca1, marca2));
		p17.getMarcas().addAll(Arrays.asList(marca1));
		p18.getMarcas().addAll(Arrays.asList(marca1));
		
		p7.getMarcas().addAll(Arrays.asList(marca2));
		p8.getMarcas().addAll(Arrays.asList(marca2));
		p9.getMarcas().addAll(Arrays.asList(marca2));
		p10.getMarcas().addAll(Arrays.asList(marca2));
		p11.getMarcas().addAll(Arrays.asList(marca2));
		p12.getMarcas().addAll(Arrays.asList(marca2));
		p19.getMarcas().addAll(Arrays.asList(marca2));
		p20.getMarcas().addAll(Arrays.asList(marca2));
		p21.getMarcas().addAll(Arrays.asList(marca2));
		p22.getMarcas().addAll(Arrays.asList(marca2));
		p23.getMarcas().addAll(Arrays.asList(marca2));
		p24.getMarcas().addAll(Arrays.asList(marca2));
		
		p1.getSituacoes().addAll(Arrays.asList(situacao1, situacao2));
		p2.getSituacoes().addAll(Arrays.asList(situacao1));
		p3.getSituacoes().addAll(Arrays.asList(situacao1));
		p4.getSituacoes().addAll(Arrays.asList(situacao1));
		p5.getSituacoes().addAll(Arrays.asList(situacao1));
		p6.getSituacoes().addAll(Arrays.asList(situacao1));
		p13.getSituacoes().addAll(Arrays.asList(situacao1, situacao2));
		p14.getSituacoes().addAll(Arrays.asList(situacao1));
		p15.getSituacoes().addAll(Arrays.asList(situacao1));
		p16.getSituacoes().addAll(Arrays.asList(situacao1));
		p17.getSituacoes().addAll(Arrays.asList(situacao1));
		p18.getSituacoes().addAll(Arrays.asList(situacao1));
		
		p7.getSituacoes().addAll(Arrays.asList(situacao2));
		p8.getSituacoes().addAll(Arrays.asList(situacao2));
		p9.getSituacoes().addAll(Arrays.asList(situacao2));
		p10.getSituacoes().addAll(Arrays.asList(situacao2));
		p11.getSituacoes().addAll(Arrays.asList(situacao2));
		p12.getSituacoes().addAll(Arrays.asList(situacao2));
		p19.getSituacoes().addAll(Arrays.asList(situacao2));
		p20.getSituacoes().addAll(Arrays.asList(situacao2));
		p21.getSituacoes().addAll(Arrays.asList(situacao2));
		p22.getSituacoes().addAll(Arrays.asList(situacao2));
		p23.getSituacoes().addAll(Arrays.asList(situacao2));
		p24.getSituacoes().addAll(Arrays.asList(situacao2));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		cli1.getTelefones().addAll(Arrays.asList("32584563", "32592828"));
		cli2.getTelefones().addAll(Arrays.asList("32584564", "32592829"));
		cli3.getTelefones().addAll(Arrays.asList("9984564", "96592829"));
		
		user1.getTelefones().addAll(Arrays.asList("32584562"));
		user2.getTelefones().addAll(Arrays.asList("32584563"));
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3, e4));
		cli3.getEnderecos().addAll(Arrays.asList(e5));
		
		user1.getEnderecos_usuario().addAll(Arrays.asList(eu1, eu2));
		user2.getEnderecos_usuario().addAll(Arrays.asList(eu3, eu4));
		user2.addPerfil(Perfil.ADMIN);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip3));
		
				
		grupoRepository.saveAll(Arrays.asList(grupo1, grupo2, grupo3, grupo4, grupo5, grupo6, grupo7, grupo8, grupo9, grupo10, grupo11, grupo12));
		
		marcaRepository.saveAll(Arrays.asList(marca1, marca2));

		situacaoRepository.saveAll(Arrays.asList(situacao1, situacao2));
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23, p24));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3));
		
		usuarioRepository.saveAll(Arrays.asList(user1, user2));
		
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3, e4, e5));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
