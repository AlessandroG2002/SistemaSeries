package br.unitins.series.model;

import java.util.ArrayList;
import java.util.List;

public enum Perfil {
	ADMINISTRADOR(1, "Administrador"), MEMBRO(2, "Membro");

	private Integer id;
	private String label;
	private List<String> paginasComPermissao;

	Perfil(Integer id, String label) {
		this.id = id;
		this.label = label;
		paginasComPermissao = new ArrayList<String>();

		// acesso para todos os usuarios
		paginasComPermissao.add("/Series/pages/sempermissao.xhtml");
		paginasComPermissao.add("/Series/pages/img-usuario.xhtml");
		paginasComPermissao.add("/Series/pages/usuarios.xhtml");
		paginasComPermissao.add("/Series/pages/series.xhtml");
		paginasComPermissao.add("/Series/pages/filmes.xhtml");
		paginasComPermissao.add("/Series/pages/serieespecifica.xhtml");
		paginasComPermissao.add("/Series/pages/filmeespecifico.xhtml");
		paginasComPermissao.add("/Series/pages/avaliacaofilme.xhtml");
		paginasComPermissao.add("/Series/pages/avaliacaofilmelisting.xhtml");
		paginasComPermissao.add("/Series/pages/avaliacaoserie.xhtml");
		paginasComPermissao.add("/Series/pages/avaliacaoserielisting.xhtml");
		paginasComPermissao.add("/Series/pages/editarconta.xhtml");
		paginasComPermissao.add("/Series/pages/perfil.xhtml");

		if (id.equals(1)) { // Administrador
			paginasComPermissao.add("/Series/pages/usuario.xhtml");
			paginasComPermissao.add("/Series/pages/usuariolisting.xhtml");
			paginasComPermissao.add("/Series/pages/serie.xhtml");
			paginasComPermissao.add("/Series/pages/serielisting.xhtml");
			paginasComPermissao.add("/Series/pages/filme.xhtml");
			paginasComPermissao.add("/Series/pages/filmelisting.xhtml");
			paginasComPermissao.add("/Series/pages/estudio.xhtml");
			paginasComPermissao.add("/Series/pages/estudiolisting.xhtml");
		} else if (id.equals(2)) { // Membro

		}
	}

	public List<String> getPaginasComPermissao() {
		return paginasComPermissao;
	}

	public static Perfil valueOf(Integer id) {
		if (id == null)
			return null;
		for (Perfil perfil : Perfil.values()) {
			if (perfil.getId().equals(id))
				return perfil;
		}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public Integer getId() {
		return id;
	}
}