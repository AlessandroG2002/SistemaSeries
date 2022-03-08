package br.unitins.series.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.application.Util;
import br.unitins.series.model.RecuperarSenha;
import br.unitins.series.model.Usuario;
import br.unitins.series.repository.RecuperarSenhaRepository;
import br.unitins.series.repository.UsuarioRepository;

@Named
@ViewScoped
public class RecuperarSenhaController extends Controller<Usuario> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2656613673466923277L;

	private RecuperarSenha pedido = null;
	private String codigo;
	private String confirmarSenha;

	private boolean verificarSenha() {
		if (getConfirmarSenha().length() < 3) {
			Util.addErrorMessage("A senha deve conter no mínimo 3 caracteres.");
			return false;
		} else if (getEntity().getSenha().equals(getConfirmarSenha())) {
			return true;
		}
		Util.addErrorMessage("As senhas estão diferentes.");
		return false;
	}

	private boolean verificarCodigo(RecuperarSenha pedido, RecuperarSenhaRepository repoSenha) {
		UsuarioRepository repoUsuario = new UsuarioRepository();

		System.out.println("Data atual: " + LocalDateTime.now() + " | Data do pedido: " + pedido.getDataLimite());
		System.out.println(pedido.getUtilizado());

		if (ChronoUnit.SECONDS.between(LocalDateTime.now(), pedido.getDataLimite()) >= 0
				&& pedido.getUtilizado() == false) {
			try {
				// entity = new Usuario();
				setEntity(repoUsuario.findById(pedido.getUsuario().getId()));
				pedido.setUtilizado(true);
				repoSenha.save(pedido);
				System.out.println("\n\n\nentrou no if\n\n\n");
				return true;
			} catch (RepositoryException e) {
				Util.addErrorMessage(e.getMessage());
				return false;
			}
		} else if (pedido.getUtilizado() == true) {
			Util.addErrorMessage("O código já foi utilizado.");
			return false;
		} else {
			Util.addErrorMessage("O código já ultrapassou seu tempo limite.");
			return false;
		}
	}

	public void recuperar() {
		if (codigo == null) {
			Util.addErrorMessage("Preencha o código.");
		}

		if (!verificarSenha()) {
			return;
		}

		RecuperarSenhaRepository repoSenha = new RecuperarSenhaRepository();

		try {
			pedido = repoSenha.findByCodigo(codigo);
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!verificarCodigo(pedido, repoSenha)) {
			return;
		}

		System.out.println("Login indo pro hash: " + getEntity().getLogin());
		System.out.println("Senha indo pro hash: " + confirmarSenha);
		String hash = Util.hash(confirmarSenha + getEntity().getLogin());
		entity.setSenha(hash);

		salvar();
		Util.redirect("/Series/login.xhtml");
	}

	@Override
	public Usuario getEntity() {
		if (entity == null) {
			entity = new Usuario();
		}
		return entity;
	}

	public RecuperarSenha getPedido() {
		if (pedido == null) {
			pedido = new RecuperarSenha();
		}
		return pedido;
	}

	public void setPedido(RecuperarSenha pedido) {
		this.pedido = pedido;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
}