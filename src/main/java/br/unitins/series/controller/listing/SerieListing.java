package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Serie;
import br.unitins.series.repository.SerieRepository;

@Named
@ViewScoped
public class SerieListing extends Listing<Serie> {

	private static final long serialVersionUID = 3362605174318519710L;
	private String filtro;
	
	public SerieListing() {
		super("pessoafisicalisting", new SerieRepository());
	}
	
	@Override
	public void pesquisar() {
		SerieRepository repo = new SerieRepository();
		try {
			setList(repo.findByTitulo(filtro));
		} catch (RepositoryException e) {
			setList(new ArrayList<Serie>());
		}
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


}