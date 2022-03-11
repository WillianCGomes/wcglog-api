package com.wcg.wcglog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wcg.wcglog.domain.model.Cliente;
import com.wcg.wcglog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired // Anotacao que define uma instancia gerenciada pelo Spring
	private ClienteRepository clienteRepository;

	// Metodo responsavel por listar todos os clientes com a requisicao get
	@GetMapping
	public List<Cliente> listar() {

		// Pegando todos os objetos de Cliente e retornando como lista
		// return manager.createQuery("from Cliente", Cliente.class).getResultList();
		return clienteRepository.findAll();
	}

	// Metodo responsavel por listar os clientes pelo id da requisicao get
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {

		return clienteRepository.findById(clienteId).map(cliente -> ResponseEntity.ok(cliente))
				.orElse(ResponseEntity.notFound().build());
		/*
		 * Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		 * 
		 * // Caso exista o cliente com id requerido, passa o codigo HTTP 200(ok) if
		 * (cliente.isPresent()) { return ResponseEntity.ok(cliente.get()); } // Caso
		 * nao exista o cliente com o id requerido, passa o codigo HTTP 404(Not Found)
		 * return ResponseEntity.notFound().build();
		 */
	}

	// Metodo responsavel por criar clientes atraves da requisicao post
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// Anotacao RequestBody para transformar o JSON em um objeto Java
	public Cliente adicionar(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	// Metodo responsavel por atualizar um cliente existente atraves da requisicao put

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId,
		@RequestBody Cliente cliente) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId); //forcando a atualizacao para nao acabar criando um novo cliente
		cliente = clienteRepository.save(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	//Metodo responsavel por deletar um cliente existente atraves da requisicao delete
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		clienteRepository.deleteById(clienteId);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
