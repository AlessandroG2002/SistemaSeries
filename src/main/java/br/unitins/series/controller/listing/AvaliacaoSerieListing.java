package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Serie;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.AvaliacaoRepository;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class AvaliacaoSerieListing extends Listing<Avaliacao> {

	private static final long serialVersionUID = -4010944566429542698L;
	private Serie filtro;

	public AvaliacaoSerieListing() {
		super("avaliacaoserielisting", new AvaliacaoRepository());
		filtro = ((Serie) Session.getInstance().get("serieSelecionada"));
		pesquisar();
	}

	@Override
	public void pesquisar() {
		AvaliacaoRepository repo = new AvaliacaoRepository();
		try {
			setList(repo.findByMidia(filtro));
		} catch (RepositoryException e) {
			setList(new ArrayList<Avaliacao>());
		}
	}

	public void selecionarUsuario(Usuario usuario) {
		UsuarioRepository repo = new UsuarioRepository();
		Usuario usuarioSelecionado = null;

		try {
			usuarioSelecionado = repo.findById(usuario.getId());
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (usuarioSelecionado != null) {
			Session.getInstance().set("usuarioSelecionado", usuarioSelecionado);
			System.out.println("Nome do usuário selecionado: " + usuarioSelecionado.getPrimeiroNome());
			Util.redirect("/Series/pages/perfil.xhtml");
		}

		Util.addErrorMessage("Erro ao selecionar usuário.");
	}

	public Serie getFiltro() {
		return filtro;
	}

	public void setFiltro(Serie filtro) {
		this.filtro = filtro;
	}

}