/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Marcus
 */
public class Tester {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("Jønke", 1963);
        Person p2 = new Person("Freddy", 1943);
        
        Address a1 = new Address("Store torv", 8000, "Aarhus");
        Address a2 = new Address("lille torv", 8732, "Hovedgaard");
        p1.setAddress(a1);
        p2.setAddress(a2);
        
        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        
        p1.addFee(f1);
        p2.addFee(f2);
        
        SwimStyle s1 = new SwimStyle("crawl");
        SwimStyle s2 = new SwimStyle("butterfly");
        SwimStyle s3 = new SwimStyle("bryst");
        
        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);
        
        em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
            p1.removeSwimStyle(s3);
        em.getTransaction().commit();
        
        System.out.println("p1: " + p1.getP_id() + " name: " + p1.getName());
        System.out.println("p2: " + p2.getP_id() + " name: " + p2.getName());
        
        System.out.println("Jønkes gade: " + p1.getAddress().getStreet());
        
        System.out.println("tovejs" + a1.getPerson().getName());
    }
}
