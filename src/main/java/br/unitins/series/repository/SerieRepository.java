package br.unitins.series.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.Serie;

public class SerieRepository extends Repository<Serie>{

	@SuppressWarnings("unchecked")
	public List<Serie> findByTitulo(String titulo) throws RepositoryException {
		try { 
			EntityManager em = getEntityManager();
			//JPQL ou SQL
			Query query = em.createQuery("SELECT u FROM Serie u WHERE upper(u.titulo) LIKE upper(:titulo)");
			query.setParameter("titulo", "%" + titulo + "%");
			
			return query.getResultList();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar séries.");
		}
	}
	
}