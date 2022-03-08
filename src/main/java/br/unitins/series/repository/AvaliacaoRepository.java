package br.unitins.series.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Avaliacao;
import br.unitins.series.model.Midia;
import br.unitins.series.model.Usuario;

public class AvaliacaoRepository extends Repository<Avaliacao> {

	@SuppressWarnings("unchecked")
	public List<Avaliacao> findByUsuario(Usuario usuario) throws RepositoryException {
		try {
			EntityManager em = getEntityManager();
			// JPQL ou SQL
			Query query = em.createQuery("SELECT u FROM Avaliacao u WHERE u.usuario = :usuario");
			query.setParameter("usuario", usuario);

			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar avaliações.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Avaliacao> findByMidia(Midia midia) throws RepositoryException {
		try {
			EntityManager em = getEntityManager();
			// JPQL ou SQL
			Query query = em.createQuery("SELECT u FROM Avaliacao u WHERE u.midia = :midia");
			query.setParameter("midia", midia);

			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar avaliações.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Avaliacao> findByMidia(String titulo) throws RepositoryException {
		try {
			EntityManager em = getEntityManager();
			// JPQL ou SQL
			Query query = em.createQuery("SELECT u FROM Avaliacao u WHERE upper(u.midia.titulo) LIKE upper(:titulo)");
			query.setParameter("titulo", "%" + titulo + "%");

			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar avaliações.");
		}
	}

	@SuppressWarnings("unchecked")
	public Avaliacao findByUsuarioAndMidia(Usuario usuario, Midia midia) throws RepositoryException {
		try {
			EntityManager em = getEntityManager();
			// JPQL ou SQL
			Query query = em.createQuery("SELECT u FROM Avaliacao u WHERE u.usuario = :usuario AND u.midia = :midia");
			query.setParameter("usuario", usuario);
			query.setParameter("midia", midia);

			return (Avaliacao) query.getSingleResult();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar avaliação especifica.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Avaliacao> findByUsuarioAndMidia(Usuario usuario, String titulo) throws RepositoryException {
		try {
			EntityManager em = getEntityManager();
			// JPQL ou SQL
			Query query = em.createQuery(
					"SELECT u FROM Avaliacao u WHERE u.usuario = :usuario AND upper(u.midia.titulo) LIKE upper(:titulo)");
			query.setParameter("usuario", usuario);
			query.setParameter("titulo", "%" + titulo + "%");

			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar avaliação especifica.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Avaliacao> findByUsuarioAndMidia(Midia midia, String nome) throws RepositoryException {
		try {
			EntityManager em = getEntityManager();
			// JPQL ou SQL
			Query query = em.createQuery(
					"SELECT u FROM Avaliacao u WHERE u.midia = :midia AND upper(u.usuario.primeiroNome) LIKE upper(:nome)");
			query.setParameter("midia", midia);
			query.setParameter("nome", "%" + nome + "%");

			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar avaliação especifica.");
		}
	}
}