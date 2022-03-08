package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Estudio;
import br.unitins.series.repository.EstudioRepository;

@Named
@ViewScoped
public class EstudioListing extends Listing<Estudio> {

	private static final long serialVersionUID = -4010944566429542698L;
	private String filtro = "";
	
	public EstudioListing() {
		super("estudiolisting", new EstudioRepository());
		pesquisar();
	}
	
	@Override
	public void pesquisar() {
		EstudioRepository repo = new EstudioRepository();
		try {
			setList(repo.findByNome(filtro));
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