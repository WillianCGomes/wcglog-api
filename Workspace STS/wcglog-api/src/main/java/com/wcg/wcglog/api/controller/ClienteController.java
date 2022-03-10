package com.wcg.wcglog.api.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcg.wcglog.domain.model.Cliente;

@RestController
public class ClienteController {

	@PersistenceContext // Ingetando um EntityManager na variável
	private EntityManager manager;

	@GetMapping("/clientes")
	public List<Cliente> listar() {

		// Pegando todos os objetos de Cliente e retornando como lista
		return manager.createQuery("from Cliente", Cliente.class).getResultList();
	}

}
