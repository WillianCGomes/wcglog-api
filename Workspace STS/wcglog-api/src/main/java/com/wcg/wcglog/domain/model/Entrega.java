package com.wcg.wcglog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	@NotNull
	@ManyToOne // Mapeamento da chave estrangeira
	private Cliente cliente;

	@Valid
	@NotNull
	@Embedded
	private Destinatario destinatario;

	@NotNull
	private BigDecimal taxa;

	@OneToMany(mappedBy = "entrega")
	private List<Ocorrencia> ocorrencias = new ArrayList<>();

	@JsonProperty(access = Access.READ_ONLY) // Anotacao para evitar que valores possam ser passados na requisicao
	@Enumerated(EnumType.STRING)
	private StatusEntrega status;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataPedido;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);

		this.getOcorrencias().add(ocorrencia);

		return ocorrencia;

	}

}
