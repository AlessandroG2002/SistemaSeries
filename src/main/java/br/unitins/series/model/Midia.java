package br.unitins.series.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Midia extends DefaultEntity {

	@Column(nullable = false, length = 30)
	private String titulo;

	@ManyToOne
	private Estudio estudio;

	@Column(nullable = false)
	private LocalDate lancamento;

	@Column(nullable = false, length = 10)
	private String rating = "0";

	@Column(nullable = false)
	private String descricao;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "midia")
	private List<Avaliacao> avaliacao;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Estudio getEstudio() {
		return estudio;
	}

	public void setEstudio(Estudio estudio) {
		this.estudio = estudio;
	}

	public LocalDate getLancamento() {
		return lancamento;
	}

	public void setLancamento(LocalDate lancamento) {
		this.lancamento = lancamento;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}