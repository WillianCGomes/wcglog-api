package com.wcg.wcglog.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wcg.wcglog.domain.exception.NegocioException;
import com.wcg.wcglog.domain.model.Cliente;
import com.wcg.wcglog.domain.model.Entrega;
import com.wcg.wcglog.domain.model.StatusEntrega;
import com.wcg.wcglog.domain.repository.ClienteRepository;
import com.wcg.wcglog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {

	private CatalogoClienteService catalogoClienteService;
 	private EntregaRepository entregaRepository;

	@Transactional
	public Entrega solicitar(Entrega entrega) {
		Cliente cliente = catalogoClienteService.buscar(entrega.getCliente().getId());

		entrega.setCliente(cliente);
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(LocalDateTime.now());

		return entregaRepository.save(entrega);
	}

}
