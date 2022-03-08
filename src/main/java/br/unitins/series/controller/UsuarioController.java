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
import org.primefaces.model.file.UploadedFile;

import br.unitins.series.application.JPAUtil;
import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.model.Perfil;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class UsuarioController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;
	private List<Usuario> listaUsuario;
	private String filtro = "";

	private InputStream fotoInputStream = null;

	public void selecionarUsuario(Usuario usuarioSelecionado) {
		EntityManager em = JPAUtil.getEntityManager();
		setEntity(em.find(Usuario.class, usuarioSelecionado.getId()));

		Session.getInstance().set("usuarioSelecionado", entity);
		System.out.println("Nome do usuario selecionado: " + entity.getNomeCompleto());

		Util.redirect("/Series/pages/perfil.xhtml");
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

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	public void pesquisar() {
		UsuarioRepository repo = new UsuarioRepository();
		try {
			setListaUsuario(repo.findByNome(filtro));
		} catch (RepositoryException e) {
			Util.addErrorMessage(e.getMessage());
		}
	}

	@Override
	public void salvar() {
		try {
			String hash = Util.hash(getEntity().getSenha() + getEntity().getLogin());
			entity.setSenha(hash);
			salvarPrincipal();

			if (getFotoInputStream() != null) {
				// salvando a imagem
				if (!Util.saveImageUsuario(fotoInputStream, "png", getEntity().getId())) {
					Util.addErrorMessage("Erro ao salvar. Não foi possível salvar a imagem do usuário.");
					return;
				}
			}
			limpar();
		} catch (RepositoryException e) {
			Util.addErrorMessage("Problema ao salvar o usuário");
		}
	}

	@Override
	public Usuario getEntity() {
		if (entity == null) {
			entity = new Usuario();
		}
		return entity;
	}

	@Override
	public void limpar() {
		super.limpar();
		listaUsuario = null;
	}

	public void editar(Integer id) {
		EntityManager em = JPAUtil.getEntityManager();
		setEntity(em.find(Usuario.class, id));

	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			listaUsuario = new ArrayList<Usuario>();
			UsuarioRepository repo = new UsuarioRepository();
			try {
				setListaUsuario(repo.findByNome(""));
			} catch (RepositoryException e) {
				Util.addErrorMessage(e.getMessage());
			}
		}
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
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
