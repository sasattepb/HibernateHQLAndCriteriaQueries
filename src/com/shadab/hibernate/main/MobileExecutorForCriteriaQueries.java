package com.shadab.hibernate.main;

import java.util.List; 
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.shadab.hibernate.models.Mobile;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * @author Shadab
 * *****************Criteria Queries***********************
 * Restrictions(lt,gt,eq,like,ilike,between,isEmpty,isNotEmpty,isNull,isNotNull)
 * Orders(asc,desc)
 * Projections(min,max,avg,sum, ountDistinct,rowCount)
 *
 */
public class MobileExecutorForCriteriaQueries {
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
      MobileExecutorForCriteriaQueries mobileExecutorForCriteriaQueries = new MobileExecutorForCriteriaQueries();

      /* Add few mobile records in database */
      Integer mobile1 = mobileExecutorForCriteriaQueries.addMobile("Nokia", "Lumia", 10000);
      Integer mobile2 = mobileExecutorForCriteriaQueries.addMobile("Apple", "7s", 60000);
      Integer mobile3 = mobileExecutorForCriteriaQueries.addMobile("Samsung", "Galaxy", 15000);

      /* List down all the mobiles */
      mobileExecutorForCriteriaQueries.listMobiles();

      /* Print Total mobile's count */
      mobileExecutorForCriteriaQueries.countMobile();

      /* Print Toatl price */
      mobileExecutorForCriteriaQueries.totalPrice();
      
      /* list mobile in order */
      mobileExecutorForCriteriaQueries.listMobilesInOrder();
      
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

   /* Method to  READ all the mobiles having price more than 10000 */
   public void listMobiles( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Criteria cr = session.createCriteria(Mobile.class);
         // Add restriction.gt,lt,eq
         cr.add(Restrictions.gt("price", 10000));
         List mobiles = cr.list();

         for (Iterator iterator = 
                           mobiles.iterator(); iterator.hasNext();){
            Mobile mobile = (Mobile) iterator.next(); 
            System.out.print(" Name: " + mobile.getName()); 
            System.out.print(" Model: " + mobile.getModel()); 
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
   /* Method to  READ all the mobiles having price more than 10000 */
   public void listMobilesInOrder( ){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Criteria cr = session.createCriteria(Mobile.class);
         // Add restriction
         cr.add(Restrictions.gt("price", 10000));
         //Add order
         cr.addOrder(Order.desc("name"));
         List mobiles = cr.list();

         for (Iterator iterator = 
                           mobiles.iterator(); iterator.hasNext();){
            Mobile mobile = (Mobile) iterator.next(); 
            System.out.print("First Name: " + mobile.getName()); 
            System.out.print("  Last Name: " + mobile.getModel()); 
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
   
   /* Method to print total number of records */
   public void countMobile(){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Criteria cr = session.createCriteria(Mobile.class);

         // To get total row count.
         cr.setProjection(Projections.rowCount());
         List rowCount = cr.list();

         System.out.println("Total Count: " + rowCount.get(0) );
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
  /* Method to print sum of salaries */
   public void totalPrice(){
      Session session = factory.openSession();
      Transaction tx = null;
      try{
         tx = session.beginTransaction();
         Criteria cr = session.createCriteria(Mobile.class);

         // To get total price.
         cr.setProjection(Projections.sum("price"));
         List totalPrice = cr.list();

         System.out.println("Total Price: " + totalPrice.get(0) );
         tx.commit();
      }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
         session.close(); 
      }
   }
}