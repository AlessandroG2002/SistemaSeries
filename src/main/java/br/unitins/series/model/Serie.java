package br.unitins.series.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Serie extends Midia {

	@Column(nullable = false, length = 4)
	private int quantCapitulos;

	@Column(nullable = false, length = 4)
	private int quantTemporada;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany(mappedBy = "seriesFavoritas")
	private List<Usuario> usuariosQueFavoritaram;

	public int getQuantCapitulos() {
		return quantCapitulos;
	}

	public void setQuantCapitulos(int quantCapitulos) {
		this.quantCapitulos = quantCapitulos;
	}

	public int getQuantTemporada() {
		return quantTemporada;
	}

	public void setQuantTemporada(int quantTemporada) {
		this.quantTemporada = quantTemporada;
	}

}