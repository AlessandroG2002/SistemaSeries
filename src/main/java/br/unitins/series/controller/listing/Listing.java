package br.unitins.series.controller.listing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.PrimeFaces;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.DefaultEntity;
import br.unitins.series.repository.Repository;

public abstract class Listing<T extends DefaultEntity> implements Serializable {

	private static final long serialVersionUID = 7641180780489288293L;
	private String page;
	private Repository<T> repository;
	private List<T> list;

	public Listing(String page, Repository<T> repository) {
		super();
		this.page = page;
		this.repository = repository;
	}

	public void open() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", true);
		options.put("width", "80%");
		options.put("height", "60%");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");

		PrimeFaces.current().dialog().openDynamic(page, options, null);

	}

	public void close() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void select(int id) {
		T obj = null;
		try {
			obj = repository.findById(id);
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		PrimeFaces.current().dialog().closeDynamic(obj);
	}

	public abstract void pesquisar();

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}