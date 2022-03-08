package br.unitins.series.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.model.Filme;
import br.unitins.series.model.Serie;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class EditarContaController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = 8004269176157113667L;

	private String antigaSenha;
	private String novaSenha;
	private String confirmarNovaSenha;
	private InputStream fotoInputStream = null;

	public void favoritarSerie() {
		System.out.println("\n\n\nTamanho antes: " + entity.getSeriesFavoritas().size());

		entity.getSeriesFavoritas().add((Serie) Session.getInstance().get("serieSelecionada"));

		System.out.println("\n\n\nTamanho depois: " + entity.getSeriesFavoritas().size() + "\n\n\n");
		salvar();
		atualizarUsuarioLogado();
		Util.redirect("/Series/pages/serieespecifica.xhtml");
	}

	public void desfavoritarSerie() {
		System.out.println("\n\n\nTamanho antes: " + entity.getSeriesFavoritas().size());

		entity.getSeriesFavoritas().remove((Serie) Session.getInstance().get("serieSelecionada"));

		System.out.println("\n\n\nTamanho depois: " + entity.getSeriesFavoritas().size() + "\n\n\n");
		salvar();
		atualizarUsuarioLogado();
		Util.redirect("/Series/pages/serieespecifica.xhtml");
	}

	public void favoritarFilme() {
		System.out.println("\n\n\nTamanho antes: " + entity.getFilmesFavoritos().size());

		entity.getFilmesFavoritos().add((Filme) Session.getInstance().get("filmeSelecionado"));

		System.out.println("\n\n\nTamanho depois: " + entity.getFilmesFavoritos().size() + "\n\n\n");
		salvar();
		atualizarUsuarioLogado();
		Util.redirect("/Series/pages/filmeespecifico.xhtml");
	}

	public void desfavoritarFilme() {
		System.out.println("\n\n\nTamanho antes: " + entity.getFilmesFavoritos().size());

		entity.getFilmesFavoritos().remove((Filme) Session.getInstance().get("filmeSelecionado"));

		System.out.println("\n\n\nTamanho depois: " + entity.getFilmesFavoritos().size() + "\n\n\n");
		salvar();
		atualizarUsuarioLogado();
		Util.redirect("/Series/pages/filmeespecifico.xhtml");
	}

	public String getAntigaSenha() {
		return antigaSenha;
	}

	public void setAntigaSenha(String antigaSenha) {
		this.antigaSenha = antigaSenha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmarNovaSenha() {
		return confirmarNovaSenha;
	}

	public void setConfirmarNovaSenha(String confirmarNovaSenha) {
		this.confirmarNovaSenha = confirmarNovaSenha;
	}

	public InputStream getFotoInputStream() {
		return fotoInputStream;
	}

	public void setFotoInputStream(InputStream fotoInputStream) {
		this.fotoInputStream = fotoInputStream;
	}

	@Override
	public Usuario getEntity() {
		if (entity == null) {
			entity = (Usuario) Session.getInstance().get("usuarioLogado");
		}
		return entity;
	}

	private boolean verificarAntigaSenha() {
		String hash = Util.hash(getAntigaSenha() + getEntity().getLogin());
		setAntigaSenha(hash);
		if (getAntigaSenha().equals(entity.getSenha())) {
			return true;
		}
		Util.addErrorMessage("A senha antiga está incorreta.");
		return false;
	}

	private boolean verificarNovaSenha() {
		if (getNovaSenha().length() < 3) {
			Util.addErrorMessage("A senha deve conter no mínimo 3 caracteres.");
			return false;
		} else if (getNovaSenha().equals(getConfirmarNovaSenha())) {
			return true;
		}
		Util.addErrorMessage("As novas senhas estão diferentes.");
		return false;
	}

	public void alterarSenha() {
		System.out.println("entrou");

		if (!verificarAntigaSenha()) {
			System.out.println(getAntigaSenha());
			System.out.println(entity.getSenha());
			System.out.println("entrou if 1 - senhas antigas erradas");
			return;
		}

		System.out.println("antigas senhas batem");

		if (!verificarNovaSenha()) {
			System.out.println(getNovaSenha());
			System.out.println(entity.getSenha());
			System.out.println("entrou if 2 - senhas novas erradas");
			return;
		}

		System.out.println("novas senhas batem");

		entity.setSenha(novaSenha);

		String hash = Util.hash(getEntity().getSenha() + getEntity().getLogin());
		entity.setSenha(hash);

		salvar();
		atualizarUsuarioLogado();
		Util.redirect("/Series/pages/editarconta.xhtml");
	}

	public void alterarSimples() {
		salvar();
		atualizarUsuarioLogado();
		Util.redirect("/Series/pages/editarconta.xhtml");
	}

	public void upload(FileUploadEvent event) {
		UploadedFile uploadFile = event.getFile();
		System.out.println("nome arquivo: " + uploadFile.getFileName());
		System.out.println("tipo: " + uploadFile.getContentType());
		System.out.println("tamanho: " + uploadFile.getSize());

		if (uploadFile.getContentType().equals("image/png")) {
			try {
				setFotoInputStream(uploadFile.getInputStream());
				System.out.println("inputStream: " + uploadFile.getInputStream().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Util.addInfoMessage("Upload realizado com sucesso.");
		} else {
			Util.addErrorMessage("O tipo da image deve ser png.");
		}

		if (getFotoInputStream() != null) {
			// salvando a imagem
			if (!Util.saveImageUsuario(fotoInputStream, "png", getEntity().getId())) {
				Util.addErrorMessage("Erro ao salvar. Não foi possível salvar a imagem da usuário.");
				return;
			}
			Util.redirect("/Series/pages/editarconta.xhtml");
		}

	}

	private void atualizarUsuarioLogado() {
		UsuarioRepository repo = new UsuarioRepository();
		Usuario usuarioLogado = null;

		try {
			usuarioLogado = repo.findById(entity.getId());
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Session.getInstance().set("usuarioLogado", usuarioLogado);
	}

}