package br.unitins.series.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.controller.listing.AvaliacaoFilmeListing;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Filme;
import br.unitins.series.model.Midia;
import br.unitins.series.repository.AvaliacaoRepository;

@Named
@ViewScoped
public class FilmeEspecificoController extends Controller<Filme> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;

	@Override
	public Filme getEntity() {
		if (entity == null) {
			entity = (Filme) Session.getInstance().get("filmeSelecionado");
		}
		return entity;
	}

	public String ratingFilme() {
		AvaliacaoRepository repo = new AvaliacaoRepository();
		List<Avaliacao> lista;
		float nota = 0;

		try {
			Midia midia = entity;
			lista = (repo.findByMidia(midia));
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

	public void abrirAvaliacaoListing() {
		AvaliacaoFilmeListing listing = new AvaliacaoFilmeListing();
		listing.open();
	}

}