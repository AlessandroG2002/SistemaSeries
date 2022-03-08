package br.unitins.series.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.controller.listing.AvaliacaoSerieListing;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Serie;
import br.unitins.series.repository.AvaliacaoRepository;

@Named
@ViewScoped
public class SerieEspecificaController extends Controller<Serie> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;
	private List<Avaliacao> listaAvaliacao;
	private String filtro = "";

	@Override
	public Serie getEntity() {
		if (entity == null) {
			entity = (Serie) Session.getInstance().get("serieSelecionada");
		}
		return entity;
	}

	public String ratingSerie() {
		AvaliacaoRepository repo = new AvaliacaoRepository();
		List<Avaliacao> lista;
		float nota = 0;

		try {
			lista = (repo.findByMidia(entity));
		} catch (RepositoryException e) {
			lista = (new ArrayList<Avaliacao>());
		}

		for (Avaliacao ava : lista) {
			if (ava.getNota() != null) {
				nota += Float.parseFloat(ava.getNota());
			}
		}

		if (lista.isEmpty() != true) {
			nota = nota / lista.size();
		}

		entity.setRating(Float.toString(nota));
		salvar();
		return entity.getRating();
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

	public void abrirAvaliacaoListing() {
		AvaliacaoSerieListing listing = new AvaliacaoSerieListing();
		listing.open();
	}

}