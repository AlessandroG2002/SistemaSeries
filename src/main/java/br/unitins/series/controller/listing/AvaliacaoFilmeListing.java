package br.unitins.series.controller.listing;

import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Session;
import br.unitins.series.application.Util;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Filme;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.AvaliacaoRepository;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class AvaliacaoFilmeListing extends Listing<Avaliacao> {

	private static final long serialVersionUID = -4010944566429542698L;
	private Filme filtro;

	public AvaliacaoFilmeListing() {
		super("avaliacaofilmelisting", new AvaliacaoRepository());
		filtro = ((Filme) Session.getInstance().get("filmeSelecionado"));
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
			System.out.println("deveria redirecionar para perfil");
		}

		Util.addErrorMessage("Erro ao selecionar usuário.");
	}

	public Filme getFiltro() {
		return filtro;
	}

	public void setFiltro(Filme filtro) {
		this.filtro = filtro;
	}

}