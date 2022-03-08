package br.unitins.series.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Estudio;

public class EstudioRepository extends Repository<Estudio> {

	public List<Estudio> findByNome(String nome) throws RepositoryException {
		return findByNome(nome, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Estudio> findByNome(String nome, Integer maxResults) throws RepositoryException {
		try { 
			EntityManager em = getEntityManager();
			//JPQL ou SQL
			Query query = em.createQuery("SELECT e FROM Estudio e WHERE upper(e.nome) LIKE upper(:nome)");
			query.setParameter("nome", "%" + nome + "%");
			
			if (maxResults != null)
				query.setMaxResults(maxResults);
			
			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar estúdios.");
		}
	}
	
	public List<Estudio> findByNomeSQL(String nome) throws RepositoryException {
		try { 
			EntityManager em = getEntityManager();
			//SQL
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  e.id, e.nome ");
			sql.append("FROM ");
			sql.append("  estudio e  ");
			sql.append("WHERE ");
			sql.append("  upper(e.nome) LIKE upper(:nome) ");
			sql.append("ORDER BY e.nome ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("nome", "%" + nome + "%");
			
			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar estúdios.");
		}
	}
}
