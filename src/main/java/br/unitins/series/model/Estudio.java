package br.unitins.series.model;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Estudio extends DefaultEntity {
	
	private String nome;
	private LocalDate dataCriacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
