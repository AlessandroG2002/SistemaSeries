package br.unitins.series.application;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("SalaAula");
	
	private JPAUtil() {	}
	
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
}
