package com.deppon.test03.model;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import junit.framework.Assert;

import com.deppon.test03.entity.PersonEntity;
import com.deppon.test03.util.HibernateUtil;

public class ModelTest {
    
	@Test
	public void testGetSession(){
		Session session = HibernateUtil.getSession();
		
		Assert.assertNotNull(session);
		HibernateUtil.closeSession();
	}
	
	@Test
	public void testExport(){
		new SchemaExport(new Configuration().configure()).create(true,true);
	}
	
	@Test
	public void testSave(){
		PersonEntity person = new PersonEntity();
		person.setId(100);
		person.setName("lufei");
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		session.save(person);
		
		tx.commit();
		HibernateUtil.closeSession();
	}
	
	@Test
	public void testQuery(){
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<PersonEntity> personList = session.createQuery("select p from PersonEntity p").list();
		
		for(PersonEntity eachPerson : personList){
			System.out.println(eachPerson);
		}
		
		session.getTransaction().commit();
		HibernateUtil.closeSession();
	}
}
