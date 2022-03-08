package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Estudio;
import br.unitins.series.repository.EstudioRepository;

@Named
@ViewScoped
public class EstudioListingSQL extends Listing<Estudio> {

	private static final long serialVersionUID = -4010944566429542698L;
	private String filtro;
	
	public EstudioListingSQL() {
		super("estudiolistingsql", new EstudioRepository());
	}
	
	@Override
	public void pesquisar() {
		EstudioRepository repo = new EstudioRepository();
		try {
			setList(repo.findByNomeSQL(filtro));
		} catch (RepositoryException e) {
			setList(new ArrayList<Estudio>());
		}
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}


}