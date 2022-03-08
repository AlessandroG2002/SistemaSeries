package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Midia;
import br.unitins.series.repository.MidiaRepository;

@Named
@ViewScoped
public class MidiaListing extends Listing<Midia> {

	private static final long serialVersionUID = -7485258844382965206L;
	private String filtro;
	
	public MidiaListing() {
		super("pessoalisting", new MidiaRepository());
	}
	
	@Override
	public void pesquisar() {
		MidiaRepository repo = new MidiaRepository();
		try {
			setList(repo.findByTitulo(filtro));
		} catch (RepositoryException e) {
			setList(new ArrayList<Midia>());
		}
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


}