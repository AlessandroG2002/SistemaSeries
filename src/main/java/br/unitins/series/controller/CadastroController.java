package br.unitins.series.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Util;
import br.unitins.series.model.Perfil;
import br.unitins.series.model.Usuario;

@Named
@ViewScoped
public class CadastroController extends Controller<Usuario> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2656613673466923277L;

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

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	@Override
	public void salvar() {
		String hash = Util.hash(getEntity().getSenha() + getEntity().getLogin());
		entity.setSenha(hash);
		entity.setPerfil(Perfil.MEMBRO);

		try {
			salvarPrincipal();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}