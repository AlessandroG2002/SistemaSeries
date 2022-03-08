package br.unitins.series.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.PrimeFaces;

import br.unitins.series.application.JPAUtil;
import br.unitins.series.application.Session;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Midia;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.AvaliacaoRepository;

@Named
@ViewScoped
public class AvaliacaoSerieController extends Controller<Avaliacao> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8068584790662156786L;

	@Override
	public Avaliacao getEntity() {
		EntityManager em = JPAUtil.getEntityManager();
		AvaliacaoRepository repo = new AvaliacaoRepository();

//		try {
//			setEntity(em.find(Avaliacao.class, repo.findByUsuarioAndMidia((Usuario) Session.getInstance().get("usuarioLogado"), (Midia) Session.getInstance().get("serieSelecionada"))));
//		} catch (RepositoryException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		if (entity == null)
			entity = new Avaliacao();
		entity.setUsuario((Usuario) Session.getInstance().get("usuarioLogado"));
		entity.setMidia((Midia) Session.getInstance().get("serieSelecionada"));
		return entity;
	}

	public void abrirAvaliacao() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", true);
		options.put("width", "80%");
		options.put("height", "60%");
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");

		PrimeFaces.current().dialog().openDynamic("avaliacaoserie", options, null);
	}

}