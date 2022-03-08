package br.unitins.series.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Avaliacao extends DefaultEntity {

	@Column(length = 255)
	private String comentario;

	@Column(nullable = false, length = 2)
	private String nota = "0";

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne()
	private Midia midia;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne()
	private Usuario usuario;

	public Midia getMidia() {
		return midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

}