package br.unitins.series.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Usuario extends DefaultEntity {

	@Column(nullable = false, length = 30, name = "primeiro_nome")
	private String primeiroNome;

	@Column(nullable = true, length = 30)
	private String sobrenome;

	@Transient
	private String nomeCompleto;

	@Email(message = "Deve ser um email válido.")
	@Column(nullable = false)
	private String login;

	@Size(min = 3, message = "A senha deve conter no mínimo 3 caracteres.")
	@Column(nullable = false)
	private String senha;

	private String bio = "Eu adoro séries!";

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "seguindo", joinColumns = @JoinColumn(name = "seguidorId"), inverseJoinColumns = @JoinColumn(name = "usuarioId"))
	private List<Usuario> seguindo;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "seguindo", joinColumns = @JoinColumn(name = "usuarioId"), inverseJoinColumns = @JoinColumn(name = "seguidorId"))
	private List<Usuario> seguidores;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "seriesFavoritas", joinColumns = @JoinColumn(name = "usuarioId"), inverseJoinColumns = @JoinColumn(name = "serieId"))
	private List<Serie> seriesFavoritas;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToMany
	@JoinTable(name = "filmesFavoritos", joinColumns = @JoinColumn(name = "usuarioId"), inverseJoinColumns = @JoinColumn(name = "filmeId"))
	private List<Filme> filmesFavoritos;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToMany(mappedBy = "usuario")
	private List<Avaliacao> avaliacao;

	private Perfil perfil;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getNomeCompleto() {
		return getPrimeiroNome() + " " + getSobrenome();
	}

	public String getBio() {
		if (bio == null) {
			bio = "Eu adoro séries!";
		}
		return bio;
	}

	public void setBio(String bio) {
		if (bio == null) {
			bio = "Eu adoro séries!";
		}
		this.bio = bio;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Usuario> getSeguindo() {
		return seguindo;
	}

	public void setSeguindo(List<Usuario> seguindo) {
		this.seguindo = seguindo;
	}

	public List<Usuario> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Usuario> seguidores) {
		this.seguidores = seguidores;
	}

	public List<Serie> getSeriesFavoritas() {
		return seriesFavoritas;
	}

	public void setSeriesFavoritas(List<Serie> seriesFavoritas) {
		this.seriesFavoritas = seriesFavoritas;
	}

	public List<Filme> getFilmesFavoritos() {
		return filmesFavoritos;
	}

	public void setFilmesFavoritos(List<Filme> filmesFavoritos) {
		this.filmesFavoritos = filmesFavoritos;
	}
}
