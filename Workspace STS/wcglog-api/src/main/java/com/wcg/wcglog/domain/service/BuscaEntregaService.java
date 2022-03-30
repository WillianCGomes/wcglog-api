package com.wcg.wcglog.domain.service;

import org.springframework.stereotype.Service;

import com.wcg.wcglog.domain.exception.NegocioException;
import com.wcg.wcglog.domain.model.Entrega;
import com.wcg.wcglog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

	private EntregaRepository entregaRepository;
	
	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
				.orElseThrow(() -> new NegocioException("Entrega não encontrada"));
	}
}
