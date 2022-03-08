package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Filme;
import br.unitins.series.repository.FilmeRepository;

@Named
@ViewScoped
public class FilmeListing extends Listing<Filme> {

	private static final long serialVersionUID = 3362605174318519710L;
	private String filtro;
	
	public FilmeListing() {
		super("pessoafisicalisting", new FilmeRepository());
	}
	
	@Override
	public void pesquisar() {
		FilmeRepository repo = new FilmeRepository();
		try {
			setList(repo.findByTitulo(filtro));
		} catch (RepositoryException e) {
			setList(new ArrayList<Filme>());
		}
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


}