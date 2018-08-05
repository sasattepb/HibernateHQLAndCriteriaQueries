package com.shadab.hibernate.namedqueries;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.shadab.hibernate.models.Mobile;

public class NamedQueriesTest {
	
	private static SessionFactory factory;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		   try{
		    	  //         factory = new AnnotationConfiguration().configure().buildSessionFactory();
		    	  //         factory = new AnnotationConfiguration().configure().addAnnotatedClass(Mobile.class).buildSessionFactory();

		         factory = new AnnotationConfiguration().configure().addPackage("com.shadab.hibernate.models.Mobile").buildSessionFactory();

		      }catch (Throwable ex) { 
		         System.err.println("Failed to create sessionFactory object." + ex);
		         throw new ExceptionInInitializerError(ex); 
		      }
		   Session session = factory.openSession();
		      Transaction tx = null;
		      Integer mobileID = null;
		      try{
		         tx = session.beginTransaction();
 		      }catch (HibernateException e) {
		         
		         e.printStackTrace(); 
		      }

		//HQL Named Query Example
		Query query = session.getNamedQuery("HQL_GET_ALL_MOBILES");
		List<Mobile> mobilesList = query.list();
		for (Mobile mobile : mobilesList) {
			System.out.println("List of Mobiles::" + mobile.getId() + ","
					+ mobile.getName());
		}

	}


}
