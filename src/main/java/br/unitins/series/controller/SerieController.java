package br.unitins.series.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;

import br.unitins.series.application.JPAUtil;
import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.controller.listing.EstudioListing;
import br.unitins.series.model.Estudio;
import br.unitins.series.model.Serie;
import br.unitins.series.repository.SerieRepository;

@Named
@ViewScoped
public class SerieController extends Controller<Serie> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;
	private List<Serie> listaSerie;
	private String filtro;

	private InputStream fotoInputStream = null;

	public void selecionarSerie(Serie serieSelecionada) {
		EntityManager em = JPAUtil.getEntityManager();
		setEntity(em.find(Serie.class, serieSelecionada.getId()));

		Session.getInstance().set("serieSelecionada", entity);
		System.out.println("Nome da série selecionada: " + entity.getTitulo());

		Util.redirect("/Series/pages/serieespecifica.xhtml");
	}

	public void upload(FileUploadEvent event) {
		UploadedFile uploadFile = event.getFile();
		System.out.println("nome arquivo: " + uploadFile.getFileName());
		System.out.println("tipo: " + uploadFile.getContentType());
		System.out.println("tamanho: " + uploadFile.getSize());

		if (uploadFile.getContentType().equals("image/png")) {
			try {
				fotoInputStream = uploadFile.getInputStream();
				System.out.println("inputStream: " + uploadFile.getInputStream().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Util.addInfoMessage("Upload realizado com sucesso.");
		} else {
			Util.addErrorMessage("O tipo da image deve ser png.");
		}

	}

	public void abrirEstudioListing() {
		EstudioListing listing = new EstudioListing();
		listing.open();
	}

	public void obterEstudioListing(SelectEvent<Estudio> event) {
		getEntity().setEstudio(event.getObject());
	}

	public void pesquisar() {
		SerieRepository repo = new SerieRepository();
		try {
			setListaSerie(repo.findByTitulo(filtro));
		} catch (RepositoryException e) {
			Util.addErrorMessage(e.getMessage());
		}
	}

	@Override
	public void salvar() {
		if (getEntity().getEstudio().getId() == null)
			getEntity().setEstudio(null);
		try {
			salvarPrincipal();

			if (getFotoInputStream() != null) {
				// salvando a imagem
				if (!Util.saveImageSerie(fotoInputStream, "png", getEntity().getId())) {
					Util.addErrorMessage("Erro ao salvar. Não foi possível salvar a imagem da série.");
					return;
				}
			}
		} catch (RepositoryException e) {
			getEntity().setEstudio(new Estudio());
			Util.addErrorMessage("Problema ao salvar a série.");
		}
	}

	@Override
	public Serie getEntity() {
		if (entity == null) {
			entity = new Serie();
			entity.setEstudio(new Estudio());

			System.out.println("\n\noutro teste\n\n");
		}
		return entity;
	}

	@Override
	public void limpar() {
		super.limpar();
		listaSerie = null;
	}

	public void editar(Integer id) {
		EntityManager em = JPAUtil.getEntityManager();
		setEntity(em.find(Serie.class, id));
		if (getEntity().getEstudio() == null)
			getEntity().setEstudio(new Estudio());

	}

	public void editarEstudio(Integer id) {
		EntityManager em = JPAUtil.getEntityManager();
		getEntity().setEstudio(em.find(Estudio.class, id));
	}

	public List<Serie> getListaSerie() {
		if (listaSerie == null) {
			listaSerie = new ArrayList<Serie>();
			SerieRepository repo = new SerieRepository();
			try {
				setListaSerie(repo.findByTitulo(""));
			} catch (RepositoryException e) {
				Util.addErrorMessage(e.getMessage());
			}
		}
		return listaSerie;
	}

	public void setListaSerie(List<Serie> listaSerie) {
		this.listaSerie = listaSerie;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public InputStream getFotoInputStream() {
		return fotoInputStream;
	}

	public void setFotoInputStream(InputStream fotoInputStream) {
		this.fotoInputStream = fotoInputStream;
	}

}