package br.unitins.series.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class LoginController extends Controller<Usuario> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2656613673466923277L;

	public String entrar() {
		UsuarioRepository repo = new UsuarioRepository();
		Usuario usuarioLogado = null;

		String hash = Util.hash(getEntity().getSenha() + getEntity().getLogin());
		entity.setSenha(hash);

		try {
			usuarioLogado = repo.validarLogin(getEntity());
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (usuarioLogado != null) {
			Session.getInstance().set("usuarioLogado", usuarioLogado);
			System.out.println("Nome do usuário logado: " + usuarioLogado.getPrimeiroNome());
			Util.redirect("/Series/pages/editarconta.xhtml");
		}

		Util.addErrorMessage("Login ou senha inválido.");
		return null;

	}

	@Override
	public void limpar() {
		entity = null;
	}

	@Override
	public Usuario getEntity() {
		if (entity == null) {
			entity = new Usuario();
		}
		return entity;
	}

}