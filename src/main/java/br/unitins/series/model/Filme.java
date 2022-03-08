package br.unitins.series.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Filme extends Midia {

	@Column(length = 8)
	private String duracao;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "filmesFavoritos")
	private List<Usuario> usuariosQueFavoritaram;

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

}