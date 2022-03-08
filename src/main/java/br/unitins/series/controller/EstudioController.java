package br.unitins.series.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.unitins.series.controller.listing.EstudioListing;
import br.unitins.series.model.Estudio;

@Named
@ViewScoped
public class EstudioController extends Controller<Estudio> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;
	
	@Override
	public Estudio getEntity() {
		if (entity == null)
			entity = new Estudio();
		return entity;
	}
	
	public void abrirEstudioListing() {
		EstudioListing listing = new EstudioListing();
		listing.open();
	}
	
	public void obterEstudioListing(SelectEvent<Estudio> event) {
		setEntity(event.getObject());
	}

	
}