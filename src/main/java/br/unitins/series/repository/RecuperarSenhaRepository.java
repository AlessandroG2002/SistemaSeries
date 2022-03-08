package br.unitins.series.repository;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unitins.series.application.RepositoryException;
import br.unitins.series.model.RecuperarSenha;

public class RecuperarSenhaRepository extends Repository<RecuperarSenha> {
	
	public RecuperarSenha findByCodigo(String codigo) throws RepositoryException {
		try { 
			EntityManager em = getEntityManager();
			//JPQL ou SQL
			Query query = em.createQuery("SELECT u FROM RecuperarSenha u WHERE u.codigo = :codigo ");
			query.setParameter("codigo", codigo);
			
			return (RecuperarSenha) query.getSingleResult();
		} catch (Exception e) {
			// mandando pro console o exception gerado
			e.printStackTrace();
			// repassando a excecao para quem vai executar o metodo
			throw new RepositoryException("Problema ao pesquisar pedidos.");
		}
	}
}