package com.shadab.hibernate.main;

import java.util.List; 
import java.util.Date;
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import com.shadab.hibernate.models.Mobile;

public class MobileExecutor {
   private static SessionFactory factory; 
   public static void main(String[] args) {
      try{
    	  //         factory = new AnnotationConfiguration().configure().buildSessionFactory();
    	  //         factory = new AnnotationConfiguration().configure().addAnnotatedClass(Mobile.class).buildSessionFactory();

         factory = new AnnotationConfiguration().configure().addPackage("com.shadab.hibernate.models.Mobile").buildSessionFactory();

      }catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
      }
      MobileExecutor mobileExecutor = new MobileExecutor();

      /* Add few mobile records in database */
      Integer mobile1 = mobileExecutor.addMobile("Nokia", "Lumia", 10000);
      Integer mobile2 = mobileExecutor.addMobile("Apple", "7s", 60000);
      Integer mobile3 = mobileExecutor.addMobile("Samsung", "Galaxy", 15000);

      /* List down all the mobiles */
      mobileExecutor.listMobile();

      /* Update mobile's records */
      mobileExecutor.updateMobile(mobile1, 5000);

      /* Delete a mobile from the database */
      mobileExecutor.deleteMobile(mobile2);

      /* List down new list of the mobiles */
      mobileExecutor.listMobile();
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
   /* Method to  READ all the mobiles */
   public void listMobile( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         List mobiles = session.createQuery("FROM Mobile").list(); 
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
   /* Method to UPDATE price for a mobile */
   public void updateMobile(Integer MobileID, int price ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Mobile mobile = 
                    (Mobile)session.get(Mobile.class, MobileID); 
         mobile.setPrice( price );
		 session.update(mobile); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
   /* Method to DELETE a mobile from the records */
   public void deleteMobile(Integer MobileID){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Mobile mobile = 
                   (Mobile)session.get(Mobile.class, MobileID); 
         session.delete(mobile); 
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
}