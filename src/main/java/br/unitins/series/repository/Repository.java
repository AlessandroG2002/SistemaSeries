package br.unitins.series.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.unitins.series.application.JPAUtil;
import br.unitins.series.application.RepositoryException;

public class Repository<T> {
	
	private EntityManager em = null;
	
	public Repository() {
		em = JPAUtil.getEntityManager();
	}
	
	public T save(T entity) throws RepositoryException {
		try { 
			getEntityManager().getTransaction().begin();
			T e = getEntityManager().merge(entity);
			getEntityManager().getTransaction().commit();
			return e;
		} catch (Exception e) {
			System.out.println("Erro ao executar o save");
			e.printStackTrace();
			throw new RepositoryException("Erro ao Salvar");
		}
	}
	
	public void remove(T entity) throws RepositoryException {
		try { 
			getEntityManager().getTransaction().begin();
			T o = getEntityManager().merge(entity);
			getEntityManager().remove(o);
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao executar o remove");
			e.printStackTrace();
			throw new RepositoryException("Erro ao Remover");
		}

	}
	
	public List<T> getAll(Class clazz) throws RepositoryException {
		try { 
			String entityName = clazz.getSimpleName();
			// jpql
			Query query = em.createQuery("SELECT u FROM "+entityName+" u");
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("Erro ao executar o getAll");
			e.printStackTrace();
			throw new RepositoryException("Erro ao Consultar no banco.");
		}

	}
	
	public T findById(int id) throws RepositoryException {
		try {
			// obtendo o tipo da classe de forma generica (a classe deve ser publica)
			final ParameterizedType type = 	(ParameterizedType) getClass().getGenericSuperclass();
			Class<T> tClass = (Class<T>) (type).getActualTypeArguments()[0];
			
			T t = (T) getEntityManager().find(tClass, id);
			return t;
		} catch (Exception e) {
			System.out.println("Erro ao executar o método find do Repository");
			e.printStackTrace();
			throw new RepositoryException("Erro ao buscar os dados");
		}
	}

	protected EntityManager getEntityManager() {
		return em;
	}
	

}
