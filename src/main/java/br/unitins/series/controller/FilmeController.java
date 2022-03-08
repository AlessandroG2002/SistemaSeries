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
import br.unitins.series.model.Filme;
import br.unitins.series.repository.EstudioRepository;
import br.unitins.series.repository.FilmeRepository;

@Named
@ViewScoped
public class FilmeController extends Controller<Filme> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;
	private List<Filme> listaFilme;
	private String filtro;

	private InputStream fotoInputStream = null;

	public void selecionarFilme(Filme filmeSelecionado) {
		EntityManager em = JPAUtil.getEntityManager();
		setEntity(em.find(Filme.class, filmeSelecionado.getId()));

		Session.getInstance().set("filmeSelecionado", entity);
		System.out.println("Nome do filme selecionado: " + entity.getTitulo());

		Util.redirect("/Series/pages/filmeespecifico.xhtml");
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
		FilmeRepository repo = new FilmeRepository();
		try {
			setListaFilme(repo.findByTitulo(filtro));
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
				if (!Util.saveImageFilme(fotoInputStream, "png", getEntity().getId())) {
					Util.addErrorMessage("Erro ao salvar. Não foi possível salvar a imagem do filme.");
					return;
				}
			}
		} catch (RepositoryException e) {
			getEntity().setEstudio(new Estudio());
			Util.addErrorMessage("Problema ao salvar o filme.");
		}
	}

	@Override
	public Filme getEntity() {
		if (entity == null) {
			entity = new Filme();
			entity.setEstudio(new Estudio());
		}
		return entity;
	}

	@Override
	public void limpar() {
		super.limpar();
		listaFilme = null;
	}

	public void editar(Integer id) {
		EntityManager em = JPAUtil.getEntityManager();
		setEntity(em.find(Filme.class, id));
		if (getEntity().getEstudio() == null)
			getEntity().setEstudio(new Estudio());

	}

	public void editarEstudio(Integer id) {
		EntityManager em = JPAUtil.getEntityManager();
		getEntity().setEstudio(em.find(Estudio.class, id));
	}

	public List<Filme> getListaFilme() {
		if (listaFilme == null) {
			listaFilme = new ArrayList<Filme>();
			FilmeRepository repo = new FilmeRepository();
			try {
				setListaFilme(repo.findByTitulo(""));
			} catch (RepositoryException e) {
				Util.addErrorMessage(e.getMessage());
			}
		}
		return listaFilme;
	}

	public void setListaFilme(List<Filme> listaFilme) {
		this.listaFilme = listaFilme;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Estudio> completeEstudio(String query) {
		EstudioRepository repo = new EstudioRepository();
		try {
			return repo.findByNome(query);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Estudio>();
	}

	public InputStream getFotoInputStream() {
		return fotoInputStream;
	}

	public void setFotoInputStream(InputStream fotoInputStream) {
		this.fotoInputStream = fotoInputStream;
	}

}