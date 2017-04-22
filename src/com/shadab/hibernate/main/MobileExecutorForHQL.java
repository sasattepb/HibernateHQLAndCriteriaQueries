package com.shadab.hibernate.main;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Restrictions;

import com.shadab.hibernate.models.Mobile;

/**
 * @author Shadab
 * ************************HQL*******************************
 * CLAUSES- from,select,as,where,group by, order by,update,delete
 * Aggregations(min,max,avg,count,sum,, special not aggregate-distinct)
 * Named Parameters
 * Pagination(setFirstResult,setMaxResults)
 *
 */
public class MobileExecutorForHQL {

	private static SessionFactory factory; 
	 private static MobileExecutorForHQL mobileExecutorForHQL;
	   public static void main(String[] args) {
	      try{
	    	  //         factory = new AnnotationConfiguration().configure().buildSessionFactory();
	    	  //         factory = new AnnotationConfiguration().configure().addAnnotatedClass(Mobile.class).buildSessionFactory();

	         factory = new AnnotationConfiguration().configure().addPackage("com.shadab.hibernate.models.Mobile").buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	       mobileExecutorForHQL = new MobileExecutorForHQL();

	      /* Add few mobile records in database */
	      Integer mobile1 = mobileExecutorForHQL.addMobile("Nokia", "Lumia", 10000);
	      Integer mobile2 = mobileExecutorForHQL.addMobile("Apple", "7s", 60000);
	      Integer mobile3 = mobileExecutorForHQL.addMobile("Samsung", "Galaxy", 15000);
	      
	      /* Test From clause*/
	      mobileExecutorForHQL.fromClauseTest();
	      /*  select test */  
	      mobileExecutorForHQL.selectClauseTest();
	      mobileExecutorForHQL.asClauseTest();
	      mobileExecutorForHQL.whereClauseTest();
	      mobileExecutorForHQL.orderBYClauseTest();
	      mobileExecutorForHQL.groupByClauseTest();
	      mobileExecutorForHQL.updateClauseTest();
	      mobileExecutorForHQL.deleteClauseTest();
	      mobileExecutorForHQL.paginationTest();
	      
	      
	      
	      
	   }
	   /* Method to CREATE a mobile in the database */
	   public Integer addMobile(String name, String model, int price){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer mobileID = null;
	      try{
	         tx = session.beginTransaction();
	         Mobile mobile = new Mobile(name, model, price);
	         mobileID = (Integer) session.save(mobile); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return mobileID;
	   }
	   
	   /* From clause test */  
	   public void fromClauseTest(){

		      Session session = factory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql = "FROM Mobile";
		         Query query = session.createQuery(hql);
		         List mobiles = query.list();

		         for (Iterator iterator = 
		                           mobiles.iterator(); iterator.hasNext();){
		            Mobile mobile = (Mobile) iterator.next(); 
		            System.out.print(" Name: " + mobile.getName()); 
		            System.out.print("   Model: " + mobile.getModel()); 
		            System.out.println("  Price: " + mobile.getPrice()); 
		         }
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
		   
	   }
	   /*  select test */  
	   public void selectClauseTest(){

		      Session session = factory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql = "select M.name FROM Mobile M";
		         Query query = session.createQuery(hql);
		         List mobiles = query.list();
System.out.println(mobiles);
		         
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
		   
	   }

	   /*  as test */ 
	   public void asClauseTest(){

		      Session session = factory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql = "FROM Mobile as M";
		         Query query = session.createQuery(hql);
		         List mobiles = query.list();

		         for (Iterator iterator = 
		                           mobiles.iterator(); iterator.hasNext();){
		            Mobile mobile = (Mobile) iterator.next(); 
		            System.out.print(" Name: " + mobile.getName()); 
		            System.out.print("   Model: " + mobile.getModel()); 
		            System.out.println("  Price: " + mobile.getPrice()); 
		         }
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
		   
	   }

	   /*  where test */  
	   public void whereClauseTest(){

		      Session session = factory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql = "FROM Mobile M WHERE M.price>1000";
		         Query query = session.createQuery(hql);
		         List mobiles = query.list();

		         for (Iterator iterator = 
		                           mobiles.iterator(); iterator.hasNext();){
		            Mobile mobile = (Mobile) iterator.next(); 
		            System.out.print(" Name: " + mobile.getName()); 
		            System.out.print("   Model: " + mobile.getModel()); 
		            System.out.println("  Price: " + mobile.getPrice()); 
		         }
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
		   
	   }

	   /*  group by test */  
	   public void groupByClauseTest(){

		      Session session = factory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql = "SELECT sum(M.price),M.name FROM Mobile M  GROUP BY M.name";
		         Query query = session.createQuery(hql);
		         List<Object[]> mobiles= (List<Object[]>)query.list();
		         for(Object[] mobile: mobiles){
		        	 System.out.println(mobile[0]);
		            System.out.println(mobile[1]);
       }
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
		   
	   }

	   /*  order by test */  
	   public void orderBYClauseTest(){

		      Session session = factory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         String hql = "FROM Mobile M ORDER BY M.name DESC";
		         Query query = session.createQuery(hql);
		         List mobiles = query.list();

		         for (Iterator iterator = 
		                           mobiles.iterator(); iterator.hasNext();){
		            Mobile mobile = (Mobile) iterator.next(); 
		            System.out.print(" Name: " + mobile.getName()); 
		            System.out.print("   Model: " + mobile.getModel()); 
		            System.out.println("  Price: " + mobile.getPrice()); 
		         }
		         tx.commit();
		      }catch (HibernateException e) {
		         if (tx!=null) tx.rollback();
		         e.printStackTrace(); 
		      }finally {
		         session.close(); 
		      }
		   
	   }

	   /*  update test */  
	   public void updateClauseTest(){
		   Session session = factory.openSession();
		   String hql = "UPDATE Mobile set model = :model";
		Query query = session.createQuery(hql);
		query.setParameter("model", "guru");
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
	   }
	   /*  delete test */  
	   public void deleteClauseTest(){
		   Session session = factory.openSession();
		   String hql = "DELETE FROM Mobile where model = :model ";
		Query query = session.createQuery(hql);
		query.setParameter("model", "guru");
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
	   }
	   
	   /* Pagination Test */
	   public void paginationTest(){
		   Session session = factory.openSession();
		   
		   try {
			      Integer mobile1 = mobileExecutorForHQL.addMobile("Nokia", "Lumia", 10000);
			      Integer mobile2 = mobileExecutorForHQL.addMobile("Apple", "7s", 60000);
			      Integer mobile3 = mobileExecutorForHQL.addMobile("Samsung", "Galaxy", 15000);
			      Integer mobile4 = mobileExecutorForHQL.addMobile("Sony", "Xperia", 10000);
			      Integer mobile5 = mobileExecutorForHQL.addMobile("Xiomi", "Redmi", 60000);
			      Integer mobile6 = mobileExecutorForHQL.addMobile("Lenovo", "Vibe", 15000);
			      Integer mobile7 = mobileExecutorForHQL.addMobile("Vivo", "YSeries", 10000);
			      Integer mobile8 = mobileExecutorForHQL.addMobile("Huawei", "MediaPad", 60000);
			      Integer mobile9 = mobileExecutorForHQL.addMobile("Samsung", "Galaxy", 15000);
			   
			String hql = "FROM Mobile";
			Query query = session.createQuery(hql);
			query.setFirstResult(1);
			query.setMaxResults(7);
			List mobiles = query.list();
			for (Iterator iterator = 
                    mobiles.iterator(); iterator.hasNext();){
     Mobile mobile = (Mobile) iterator.next(); 
     System.out.print(" Name: " + mobile.getName()); 
     System.out.print("   Model: " + mobile.getModel()); 
     System.out.println("  Price: " + mobile.getPrice()); 
  }
		} catch (Exception e) {
			// TODO: handle exception
		}
	   }
}