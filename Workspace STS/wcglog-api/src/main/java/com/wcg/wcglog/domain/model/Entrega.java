package com.wcg.wcglog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.wcg.wcglog.domain.ValidationGroups;
import com.wcg.wcglog.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne // Mapeamento da chave estrangeira
	private Cliente cliente;

	@Embedded
	private Destinatario destinatario;

	private BigDecimal taxa;

	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
	private List<Ocorrencia> ocorrencias = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private StatusEntrega status;

	private OffsetDateTime dataPedido;

	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);

		this.getOcorrencias().add(ocorrencia);

		return ocorrencia;

	}

	public void finalizar() {
		if (podeSerFinalizada()) {
			setStatus(StatusEntrega.FINALIZADA);
			setDataFinalizacao(OffsetDateTime.now());
		} else {
			throw new NegocioException("A entrega n??o pode ser finalizada");
		}
	}

	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}

}
