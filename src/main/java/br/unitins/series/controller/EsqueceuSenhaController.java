package br.unitins.series.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.Email;
import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Util;
import br.unitins.series.model.RecuperarSenha;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.RecuperarSenhaRepository;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class EsqueceuSenhaController implements Serializable {

	private static final long serialVersionUID = 1397581706694686021L;

	private String email;

	public void enviarEmail() {

		UsuarioRepository repo = new UsuarioRepository();
		Usuario usuario = null;
		try {
			usuario = repo.findByEmail(email);
		} catch (RepositoryException e) {
			Util.addErrorMessage("Email não encontrado.");
			return;
		}
		// gerando codigo aleatorio
		Random r = new Random();
		DecimalFormat format = new DecimalFormat("T-000000");
		String codigo = format.format(r.nextInt(1000000));

		RecuperarSenha entity = new RecuperarSenha();
		entity.setCodigo(codigo);
		entity.setUsuario(usuario);
		entity.setUtilizado(false);
		// definindo 24 horas de tempo limite
		entity.setDataLimite(LocalDateTime.now().plusDays(1));

		RecuperarSenhaRepository repoRecuperar = new RecuperarSenhaRepository();
		try {
			repoRecuperar.save(entity);
			Email email = new Email(usuario.getLogin(), "Esqueceu a Senha",
					codigo + " é seu código de recuperação de senha. ");
			email.enviar();
			Util.addInfoMessage("O código foi enviado para o seu email.");
			Util.redirect("/Series/recuperarsenha.xhtml");
		} catch (RepositoryException e) {
			e.printStackTrace();
			Util.addErrorMessage("Falha ao enviar o código.");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}