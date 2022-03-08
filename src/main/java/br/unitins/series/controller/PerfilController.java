package br.unitins.series.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.AvaliacaoRepository;

@Named
@ViewScoped
public class PerfilController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;
	private List<Avaliacao> listaAvaliacao;
	private String filtro = "";

	@Override
	public Usuario getEntity() {
		if (entity == null) {
			entity = (Usuario) Session.getInstance().get("usuarioSelecionado");
		}
		return entity;
	}

	public void seguir() {
		System.out.println("\n\n\nTamanho antes: " + entity.getSeguidores().size());

		entity.getSeguidores().add((Usuario) Session.getInstance().get("usuarioLogado"));

		System.out.println("\n\n\nTamanho depois: " + entity.getSeguidores().size() + "\n\n\n");
		salvar();
		Util.redirect("/Series/pages/perfil.xhtml");
	}

	public void deixarDeSeguir() {
		System.out.println("\n\n\nTamanho antes: " + entity.getSeguidores().size());

		entity.getSeguidores().remove((Usuario) Session.getInstance().get("usuarioLogado"));

		System.out.println("\n\n\nTamanho depois: " + entity.getSeguidores().size() + "\n\n\n");
		salvar();
		Util.redirect("/Series/pages/perfil.xhtml");
	}

	public void pesquisarAvaliacao() {
		AvaliacaoRepository repo = new AvaliacaoRepository();
		try {
			setListaAvaliacao(repo.findByUsuarioAndMidia(entity, filtro));
		} catch (RepositoryException e) {
			setListaAvaliacao(new ArrayList<Avaliacao>());
		}
	}

	public List<Avaliacao> getListaAvaliacao() {
		if (listaAvaliacao == null) {
			AvaliacaoRepository repo = new AvaliacaoRepository();
			try {
				listaAvaliacao = repo.findByUsuarioAndMidia(entity, filtro);
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listaAvaliacao;
	}

	public void setListaAvaliacao(List<Avaliacao> listaAvaliacao) {
		this.listaAvaliacao = listaAvaliacao;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
}