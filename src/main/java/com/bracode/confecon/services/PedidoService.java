package com.bracode.confecon.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bracode.confecon.domain.Cliente;
import com.bracode.confecon.domain.ItemPedido;
import com.bracode.confecon.domain.PagamentoBoleto;
import com.bracode.confecon.domain.Pedido;
import com.bracode.confecon.domain.Representante;
import com.bracode.confecon.domain.enums.EstadoPagamento;
import com.bracode.confecon.repositories.ItemPedidoRepository;
import com.bracode.confecon.repositories.PagamentoRepository;
import com.bracode.confecon.repositories.PedidoRepository;
import com.bracode.confecon.security.UserSS;
import com.bracode.confecon.services.exceptions.AuthorizationException;
import com.bracode.confecon.services.exceptions.ObjectNotFoundException;

@Service

public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));

	}

	@Transactional
	public Pedido insert(Pedido obj) {

		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.setRepresentante((Representante) usuarioService.find(obj.getRepresentante().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
			boletoService.preencherPagamentoBoleto(pagto, obj.getInstante());
		}

		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setFrete(ip.getProduto().getFrete());
			ip.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;

	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

}
