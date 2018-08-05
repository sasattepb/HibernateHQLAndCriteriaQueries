package com.shadab.hibernate.typedquery;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import com.shadab.hibernate.models.Mobile;


public class MobileDaoImpl {
  public void test() {

	  Mobile emp = new Mobile();

    emp.setId(1);
    emp.setName("name");
    System.out.println(em);
    em.persist(emp);

    TypedQuery<Mobile> query = em.createQuery("SELECT e FROM Mobile e",Mobile.class);
    List<Mobile> emps = query.getResultList();

  }

  @PersistenceContext
  private EntityManager em;
}