package com.qualifier;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named
@RequestScoped
public class Resource {

	 private static EntityManagerFactory factory;
	 
	 @Produces
	 public static EntityManager getEntityManager(){
		 factory = Persistence.createEntityManagerFactory("persistenceUnit");
		 EntityManager em = factory.createEntityManager();
		 return em;
	 }
	
}
